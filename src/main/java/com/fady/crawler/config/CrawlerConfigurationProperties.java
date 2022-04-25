package com.fady.crawler.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Data
@Configuration
@Validated
@ConfigurationProperties(prefix = "configuration")
public class CrawlerConfigurationProperties {

    @Valid
    private final CrawlerProperties crawler = new CrawlerProperties();

    @Data
    @Validated
    public static class CrawlerProperties {

        @Min(0)
        private int defaultDepth;

        @Min(0)
        private int maxDepthAllowed;

        @Min(5)
        private int timeOut;

        private boolean followRedirects;

    }


}
