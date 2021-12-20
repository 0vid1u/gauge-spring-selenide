package my.company.gauge.exceptions;

public class GaugeTableParsingException extends RuntimeException {

    public GaugeTableParsingException() {
    }

    public GaugeTableParsingException(String message) {
        super(message);
    }

    public GaugeTableParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public GaugeTableParsingException(Throwable cause) {
        super(cause);
    }

    public GaugeTableParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
