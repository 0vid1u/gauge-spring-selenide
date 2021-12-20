package my.company.env.mapper;

import org.aeonbits.owner.Config;

/**
 * @author otuvrila
 */

public interface RunProperties extends Config {

    @DefaultValue("chrome")
    @Key("browser")
    String getBrowser();

    @DefaultValue("false")
    @Key("in_headless")
    boolean isHeadless();

    @DefaultValue("30")
    @Key("timeout")
    long getTimeout();

    @Key("enable_html_report_log_appender")
    @DefaultValue("false")
    boolean isHtmlReportLogAppenderEnabled();
}