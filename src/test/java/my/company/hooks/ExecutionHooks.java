package my.company.hooks;

import com.thoughtworks.gauge.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutionHooks {

    @BeforeSuite
    public void beforeSuite() {
        log.info("before suite");
    }

    @AfterSuite
    public void afterSuite() {
        log.info("after suite");
    }

    @BeforeSpec
    public void beforeSpec(ExecutionContext executionContext) {
        log.info("before spec");
    }

    @AfterSpec
    public void afterSpec() {
        log.info("after spec");
    }

    @BeforeScenario
    public void beforeScenario(ExecutionContext executionContext) {
        log.info("before scenario");
    }

    @AfterScenario
    public void afterScenario() {
        log.info("after scenario");
    }

    @BeforeStep
    public void beforeStep(ExecutionContext executionContext) {
        log.info("before step");
    }

    @AfterStep
    public void afterStep() {
        log.info("after step");
    }
}
