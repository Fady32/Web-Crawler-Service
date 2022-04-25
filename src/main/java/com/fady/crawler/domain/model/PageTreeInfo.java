package com.fady.crawler.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class PageTreeInfo implements Serializable {

    private static final long serialVersionUID = -8520773347521909293L;

    @JsonProperty("url")
    private String url;

    @JsonProperty("title")
    private String title;

    @JsonProperty("valid")
    private Boolean valid;

    @JsonProperty("nodes")
    private List<PageTreeInfo> nodes;

    public PageTreeInfo(final String url) {
        this.url = url;
        valid = false;
        ;
    }

    public PageTreeInfo title(final String title) {
        this.title = title;
        return this;
    }

    public void valid(final Boolean valid) {
        this.valid = valid;
    }

    public void addNodesItem(final PageTreeInfo nodesItem) {
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        if (nodesItem != null) {
            nodes.add(nodesItem);
        }
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PageTreeInfo pageTreeInfo = (PageTreeInfo) o;
        return Objects.equals(url, pageTreeInfo.url) && Objects.equals(title, pageTreeInfo.title)
                && Objects.equals(valid, pageTreeInfo.valid) && Objects.equals(nodes, pageTreeInfo.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, title, valid, nodes);
    }

}
