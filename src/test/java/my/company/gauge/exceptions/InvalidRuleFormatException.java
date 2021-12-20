package my.company.gauge.exceptions;

public class InvalidRuleFormatException extends RuntimeException {

    public InvalidRuleFormatException() {
    }

    public InvalidRuleFormatException(String message) {
        super(message);
    }

    public InvalidRuleFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRuleFormatException(Throwable cause) {
        super(cause);
    }

    public InvalidRuleFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
