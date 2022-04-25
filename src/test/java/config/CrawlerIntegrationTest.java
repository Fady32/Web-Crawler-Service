package config;

import com.fady.crawler.CrawlerApplication;
import com.fady.crawler.config.CrawlerConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = CrawlerApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("local")
@ContextConfiguration(classes = CrawlerConfigurationProperties.class)
public @interface CrawlerIntegrationTest {

}
