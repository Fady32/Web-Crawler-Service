package com.fady.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@EnableConfigurationProperties
@SpringBootApplication
public class CrawlerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }

}
