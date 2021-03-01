package my.company.pom.pages.impl;

import lombok.SneakyThrows;
import my.company.pom.pages.ResultsPage;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.$$;

/**
 * @author otuvrila
 */

@Component
public class ResultsPageImpl extends BasePage implements ResultsPage {

    private final By resultList = By.xpath("//div[@class='g']");

    @Override
    public boolean isAt() {
        $$(resultList).shouldBe(sizeGreaterThan(0));
        return true;
    }

    @SneakyThrows
    @Override
    public int getResultsCount() {
        return $$(resultList)
                .shouldHave(sizeGreaterThan(0))
                .size();
    }
}
