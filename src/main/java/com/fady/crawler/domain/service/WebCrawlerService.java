package com.fady.crawler.domain.service;

import com.fady.crawler.domain.model.PageInfo;
import com.fady.crawler.domain.model.PageTreeInfo;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

public interface WebCrawlerService {

    @Cacheable(cacheNames = "url-scan-result")
    PageTreeInfo deepScan(String url, int depth, List<String> processedUrls, int maxResult);


    public Optional<PageInfo> scan(String url);

}
