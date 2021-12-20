package my.company.ioc;

import my.company.env.mapper.RunProperties;
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
        "my.company.gauge.*",
        "my.company.datastore.*",
        "my.company.utils"
})
public class SpringApplicationConfiguration {

    @Bean
    public RunProperties propertiesProvider() {
        return ConfigCache.getOrCreate(RunProperties.class, System.getenv());
    }
}