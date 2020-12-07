package de.mayring.reproducer;

import com.vaadin.flow.spring.annotation.EnableVaadin;

import de.mayring.base.BaseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(
        exclude = {
                SecurityAutoConfiguration.class,
                DataSourceTransactionManagerAutoConfiguration.class,
                MongoAutoConfiguration.class,
                MongoDataAutoConfiguration.class
        }
)
@EnableCaching
@EnableConfigurationProperties(
        value = {
                BaseProperties.class
        }
)
@ComponentScan(
        basePackages = {
                "de.mayring.base",
                "de.mayring.reproducer"
        }
)
@EnableVaadin(
        value = {
                "de.mayring.base",
                "de.mayring.reproducer"
        }
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
