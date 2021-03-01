package my.company.hooks;

import com.codeborne.selenide.Configuration;
import com.thoughtworks.gauge.*;
import lombok.extern.slf4j.Slf4j;
import my.company.env.PropertiesProvider;
import my.company.ioc.CustomClassInitializer;
import my.company.ioc.SpringApplicationContextHolder;
import org.aeonbits.owner.ConfigCache;

@Slf4j
public class ExecutionHooks {

    private final PropertiesProvider config = ConfigCache.getOrCreate(PropertiesProvider.class, System.getenv());

    @BeforeSuite
    public void beforeSuite() {
        setupSpringContext();
        setupSelenide();
    }

    private void setupSelenide() {
        Configuration.browser = config.getBrowser();
        Configuration.timeout = config.getTimeout();
        Configuration.pageLoadTimeout = config.getTimeout();
        Configuration.headless = config.isHeadless();
    }

    @AfterSuite
    public void afterSuite() {
    }

    @BeforeSpec
    public void beforeSpec(ExecutionContext executionContext) {
    }

    @AfterSpec
    public void afterSpec() {
        log.info("after spec");
    }

    @BeforeScenario
    public void beforeScenario(ExecutionContext executionContext) {
    }

    @AfterScenario
    public void afterScenario() {
    }

    @BeforeStep
    public void beforeStep(ExecutionContext executionContext) {
    }

    @AfterStep
    public void afterStep() {
    }

    private void setupSpringContext() {
        ClassInstanceManager.setClassInitializer(
                new CustomClassInitializer(SpringApplicationContextHolder.getInstance().getContext())
        );
    }
}
