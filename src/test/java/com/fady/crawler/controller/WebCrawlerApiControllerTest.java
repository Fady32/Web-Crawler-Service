package com.fady.crawler.controller;

import com.fady.crawler.domain.model.PageInfo;
import com.fady.crawler.domain.model.PageTreeInfo;
import com.fady.crawler.domain.service.WebCrawlerService;
import com.google.common.io.Resources;
import config.CrawlerIntegrationTest;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@CrawlerIntegrationTest
class WebCrawlerApiControllerTest {

    @Inject
    private MockMvc mockMvc;
    private PageTreeInfo pageTreeInfo;
    private PageInfo pageInfo;

    @Mock
    private WebCrawlerService crawlerService;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        pageTreeInfo = new PageTreeInfo("https://monzo.com");
        String htmlPayload = readContentAsString("monzo.com.html");
        final Elements elements = Jsoup.parse(htmlPayload).select("a[href]");
        pageInfo = new PageInfo("Test", "https://monzo.com", elements);
    }

    @Test
    public void testGetWebPageTreeInfo() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/scan").contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.when(crawlerService.scan(Mockito.anyString())).thenReturn(Optional.of(pageInfo));

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/scan?url=https://monzo.com&depth=5&maxResult=10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotEmpty();

        Mockito.when(crawlerService.deepScan(Mockito.anyString(), Mockito.anyInt(), Mockito.anyList(), Mockito.anyInt()))
                .thenReturn(pageTreeInfo);
        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/scan?url=https://monzo.com&depth=5&maxResult=10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotEmpty().contains("monzo.com");

    }

    public static String readContentAsString(final String uri) throws IOException {
        return Resources.toString(Resources.getResource(uri), Charset.defaultCharset());
    }

}