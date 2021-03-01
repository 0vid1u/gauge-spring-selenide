package my.company.pom.pages;

import my.company.pom.Page;
import org.springframework.stereotype.Component;

/**
 * @author otuvrila
 */
@Component
public interface SearchPage extends Page {
    void searchFor(String expression);
}
