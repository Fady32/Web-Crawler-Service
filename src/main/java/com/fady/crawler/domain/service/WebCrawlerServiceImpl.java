package com.fady.crawler.domain.service;

import com.fady.crawler.config.CrawlerConfigurationProperties;
import com.fady.crawler.domain.model.PageInfo;
import com.fady.crawler.domain.model.PageTreeInfo;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

    @Value("#{crawlerConfigurationProperties.crawler}")
    private CrawlerConfigurationProperties.CrawlerProperties crawlerProperties;


    @Override
    @Cacheable(cacheNames = "url-scan-result")
    public PageTreeInfo deepScan(final String url, final int depth, final List<String> processedUrls, int maxResult) {


        if (depth < 0 || maxResult < 0) {
            return null;
        } else {
            final List<String> updatedProcessedUrls = Optional.ofNullable(processedUrls).orElse(new ArrayList<>());
            if (!updatedProcessedUrls.contains(url)) {
                updatedProcessedUrls.add(url);
                final PageTreeInfo pageTreeInfo = new PageTreeInfo(url);
                AtomicInteger finalMaxResult = new AtomicInteger(maxResult);
                scan(url).ifPresent(pageInfo -> {
                    URI uri = null;
                    try {
                        uri = new URI(pageInfo.getUrl());
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    String domain = Objects.requireNonNull(uri).getHost();
                    if (pageInfo.getUrl().contains(domain)) {
                        pageTreeInfo.title(pageInfo.getTitle()).valid(true);
                        int limit = finalMaxResult.get();
                        for (Element link : pageInfo.getLinks()) {
                            if (limit-- < 0) break;
                            PageTreeInfo treeInfo = deepScan(link.attr("abs:href"), depth - 1, updatedProcessedUrls, limit);
                            if (treeInfo != null) {
                                pageTreeInfo.addNodesItem(treeInfo);
                                limit = finalMaxResult.decrementAndGet();
                                finalMaxResult.set(limit);
                            }
                        }
                    }

                });
                return pageTreeInfo;
            } else {
                return null;
            }
        }

    }

    @Override
    @Cacheable(cacheNames = "url-scan-result")
    public Optional<PageInfo> scan(final String url) {
        try {
            final Document doc = Jsoup.connect(url).timeout(crawlerProperties.getTimeOut())
                    .followRedirects(crawlerProperties.isFollowRedirects()).get();

            final Elements links = doc.select("a[href]");
            final String title = doc.title();

            return Optional.of(new PageInfo(title, url, links));
        } catch (final IOException | IllegalArgumentException e) {
            log.error(String.format("Error getting contents of url %s", url), e);
            return Optional.empty();
        }

    }

}
