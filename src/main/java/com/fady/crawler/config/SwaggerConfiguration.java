package com.fady.crawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.stream.Collectors;
import java.util.stream.Stream;


@Configuration
public class SwaggerConfiguration {

    @Bean
    Docket swagger() {
        return new Docket(DocumentationType.OAS_30).useDefaultResponseMessages(false)
                .produces(Stream.of("application/xml", "application/json").collect(Collectors.toSet())).select()
                .paths(PathSelectors.ant("/*/scan")).build()
                .protocols(Stream.of("http", "https").collect(Collectors.toSet()));
    }
}
