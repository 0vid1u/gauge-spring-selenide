package my.company.extensions.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.google.common.html.HtmlEscapers;
import com.thoughtworks.gauge.Gauge;
import my.company.env.Configuration;
import org.aeonbits.owner.ConfigCache;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GaugeHtmlReportAppender extends AppenderBase<ILoggingEvent> {
    private static final Configuration CONFIG = ConfigCache.getOrCreate(Configuration.class, System.getenv());
    private static final String SPACE = " ";
    private static final String HTML_SPACE_CODE = "&nbsp;";
    private static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy HH:mm:ss.SSS";

    @Override
    protected void append(ILoggingEvent event) {
        if (CONFIG.isHtmlReportLogAppenderEnabled()) {
            Gauge.writeMessage(buildLogMessage(event));
        }
    }

    private String buildLogMessage(ILoggingEvent event) {
        return formatTimeStamp(event.getTimeStamp()) + SPACE
               + event.getLevel() + SPACE
               + HtmlEscapers.htmlEscaper()
                       .escape(event.getFormattedMessage())
                       .replace(SPACE, HTML_SPACE_CODE);
    }

    private String formatTimeStamp(long timeStamp) {
        return new SimpleDateFormat(DATE_FORMAT_PATTERN).format(new Date(timeStamp));
    }
}