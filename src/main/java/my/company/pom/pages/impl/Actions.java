package my.company.pom.pages.impl;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;

/**
 * @author otuvrila
 */

@Slf4j
public class Actions {

    public void clickOn(SelenideElement element) {
        element.click();
        log.info("Click on {}", element.getSearchCriteria());
    }

    public void setValue(SelenideElement element, String value) {
        element.setValue(value);
        log.info("Set value {} in {}", value, element.getSearchCriteria());
    }
}
