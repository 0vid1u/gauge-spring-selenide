package my.company.gauge.exceptions;

public class NullBeanPropertyException extends RuntimeException {
    public NullBeanPropertyException() {
    }

    public NullBeanPropertyException(String message) {
        super(message);
    }

    public NullBeanPropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullBeanPropertyException(Throwable cause) {
        super(cause);
    }

    public NullBeanPropertyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
