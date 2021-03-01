package my.company.ioc;

import my.company.env.PropertiesProvider;
import my.company.pom.pages.ResultsPage;
import my.company.pom.pages.SearchPage;
import my.company.pom.pages.impl.ResultsPageImpl;
import my.company.pom.pages.impl.SearchPageImpl;
import org.aeonbits.owner.ConfigCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SuppressWarnings({"SpringFacetCodeInspection", "SpringComponentScan"})
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {
        "my.company.pom.pages.*",
        "my.company.ioc.aspects.*",
        "my.company.steps.*"
})
public class SpringApplicationConfiguration {

    @Bean
    public PropertiesProvider propertiesProvider() {
        return ConfigCache.getOrCreate(PropertiesProvider.class, System.getenv());
    }
}