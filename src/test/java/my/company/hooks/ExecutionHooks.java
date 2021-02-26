package my.company.hooks;

import com.thoughtworks.gauge.*;
import lombok.extern.slf4j.Slf4j;
import my.company.ioc.CustomClassInitializer;
import my.company.ioc.SpringApplicationContextHolder;

@Slf4j
public class ExecutionHooks {

    @BeforeSuite
    public void beforeSuite() {
        setupSpringContext();
        setupSelenide();
    }

    private void setupSelenide() {
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
