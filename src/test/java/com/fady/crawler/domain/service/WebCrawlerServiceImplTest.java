package com.fady.crawler.domain.service;

import com.fady.crawler.domain.model.PageInfo;
import com.fady.crawler.domain.model.PageTreeInfo;
import config.CrawlerIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@CrawlerIntegrationTest
class WebCrawlerServiceImplTest {

    @Autowired
    private WebCrawlerService crawlerService;

    @Test
    public void testDeepCrawl() {
        final PageTreeInfo info = crawlerService.deepScan("https://monzo.com", 1, null, 5);
        assertThat(info).isNotNull().satisfies(treeInfo -> {
            assertThat(treeInfo.getTitle()).contains("Monzo – Banking made easy");
            assertThat(treeInfo.getUrl()).contains("https://monzo.com");
            assertThat(treeInfo.getNodes().size()).isEqualTo(5);
        });
    }

    @Test
    public void testCrawl() {
        final Optional<PageInfo> info = crawlerService.scan("https://monzo.com");
        assertThat(info).isPresent();
        assertThat(info.get().getTitle()).contains("Monzo – Banking made easy");
        assertThat(info.get().getUrl()).contains("https://monzo.com");
        assertThat(info.get().getLinks().size()).isGreaterThan(10);
    }
}