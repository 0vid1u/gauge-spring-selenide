package my.company.gauge.hooks;

import com.codeborne.selenide.Configuration;
import com.thoughtworks.gauge.*;
import lombok.extern.slf4j.Slf4j;
import my.company.ioc.CustomClassInitializer;
import my.company.ioc.SpringApplicationContextHolder;
import org.aeonbits.owner.ConfigCache;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class ExecutionHooks {

    private static final my.company.env.Configuration CONFIG = ConfigCache.getOrCreate(my.company.env.Configuration.class, System.getenv());
    private static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy HH:mm:ss.SSS";

    private static String getCurrentTimestamp() {
        return new SimpleDateFormat(DATE_FORMAT_PATTERN).format(new Date(System.currentTimeMillis()));
    }

    @BeforeSuite
    public void beforeSuite() {
        setupSpringContext();
        setupSelenide();
    }

    private void setupSelenide() {
        Configuration.browser = CONFIG.getBrowser();
        Configuration.timeout = CONFIG.getTimeout();
        Configuration.pageLoadTimeout = CONFIG.getTimeout();
        Configuration.headless = CONFIG.isHeadless();
    }

    @AfterSuite
    public void afterSuite() {
    }

    @BeforeSpec
    public void beforeSpec(ExecutionContext executionContext) {
        System.out.println(getCurrentTimestamp() + "  |-  # " + executionContext.getCurrentSpecification().getName());
    }

    @AfterSpec
    public void afterSpec() {
        log.info("after spec");
    }

    @BeforeScenario
    public void beforeScenario(ExecutionContext executionContext) {
        System.out.println(getCurrentTimestamp() + "  |- ## " + executionContext.getCurrentScenario().getName());
    }

    @AfterScenario
    public void afterScenario() {
    }

    @BeforeStep
    public void beforeStep(ExecutionContext executionContext) {
        System.out.println(getCurrentTimestamp() + "  |-  * " + executionContext.getCurrentStep().getDynamicText());
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
