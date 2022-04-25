package com.fady.crawler.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jsoup.select.Elements;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PageInfo implements Serializable {

    private static final long serialVersionUID = 1773875051659981029L;

    private String title;

    private String url;

    private Elements links;
}
