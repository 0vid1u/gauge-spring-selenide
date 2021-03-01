package my.company.pom.pages;

import my.company.pom.Page;
import org.springframework.stereotype.Component;

/**
 * @author otuvrila
 */
@Component
public interface ResultsPage extends Page {
    int getResultsCount();
}
