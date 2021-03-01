package my.company.env;

import org.aeonbits.owner.Config;

import static org.aeonbits.owner.Config.*;

/**
 * @author otuvrila
 */

public interface PropertiesProvider extends Config {

    @DefaultValue("chrome")
    @Key("browser")
    String getBrowser();

    @DefaultValue("false")
    @Key("in_headless")
    boolean isHeadless();

    @DefaultValue("30")
    @Key("timeout")
    long getTimeout();
}
