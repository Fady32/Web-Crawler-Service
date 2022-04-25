package com.fady.crawler.controller;

import com.fady.crawler.config.CrawlerConfigurationProperties;
import com.fady.crawler.domain.model.PageTreeInfo;
import com.fady.crawler.domain.service.WebCrawlerService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@OpenAPIDefinition(
        info = @Info(title = "Web Crawler Service endpoints", version = "v1")
)
@Slf4j
@RestController
public class WebCrawlerApiController {

    private final WebCrawlerService crawlerService;

    @Value("#{crawlerConfigurationProperties.crawler}")
    private CrawlerConfigurationProperties.CrawlerProperties crawlerProperties;

    public WebCrawlerApiController(WebCrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @GetMapping(value = "/scan", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageTreeInfo> getWebPageTreeInfo(
            @NotNull @RequestParam(value = "url", required = true) final String url,
            @RequestParam(value = "depth", required = false) final Integer depth,
            @RequestParam(value = "maxResult", required = false) final Integer maxResult) {

        final int newDepth = Integer.min(Optional.ofNullable(depth).orElse(crawlerProperties.getDefaultDepth()),
                crawlerProperties.getMaxDepthAllowed());

        return new ResponseEntity<>(crawlerService.deepScan(url, newDepth, null, maxResult), HttpStatus.OK);
    }

}
