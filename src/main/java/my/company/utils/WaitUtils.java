package my.company.utils;

import lombok.extern.slf4j.Slf4j;
import my.company.env.Configuration;
import org.aeonbits.owner.ConfigCache;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@Slf4j
public final class WaitUtils {

    private static final Configuration CONFIG = ConfigCache.getOrCreate(Configuration.class, System.getenv());

    private WaitUtils() {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }

    public static void waitFor(Duration duration) {
        waitForMillis(duration.toMillis());
    }

    public static void waitFor(long value, TimeUnit unit) {
        waitForMillis(of(value, unit).toMillis());
    }

    public static void waitForMillis(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public static boolean awaitForElements(long timeoutInSeconds, WebElement... elements) {
        try {
            await().ignoreExceptions().atMost(timeoutInSeconds, TimeUnit.SECONDS).until(() -> {
                boolean isDisplayed = true;
                for (var element : elements) {
                    boolean currentElementIsDisplayed = element.isDisplayed();
                    if (!currentElementIsDisplayed) {
                        log.warn("The {} is not displayed", element);
                    }
                    isDisplayed = isDisplayed && currentElementIsDisplayed;
                }
                return isDisplayed;
            });
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }

    public static boolean awaitForElements(WebElement... elements) {
        return awaitForElements(CONFIG.getTimeout(), elements);
    }

    public static boolean awaitForCondition(long timeoutInSeconds, Callable<Boolean> condition) {
        try {
            await().ignoreExceptions().atMost(timeoutInSeconds, TimeUnit.SECONDS).until(condition);
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }

    public static boolean awaitForCondition(Callable<Boolean> condition) {
        return awaitForCondition(CONFIG.getTimeout(), condition);
    }

    private static Duration of(long amount, TimeUnit timeUnit) {
        final ChronoUnit chronoUnit = switch (timeUnit) {
            case NANOSECONDS -> ChronoUnit.NANOS;
            case MICROSECONDS -> ChronoUnit.MICROS;
            case MILLISECONDS -> ChronoUnit.MILLIS;
            case SECONDS -> ChronoUnit.SECONDS;
            case MINUTES -> ChronoUnit.MINUTES;
            case HOURS -> ChronoUnit.HOURS;
            case DAYS -> ChronoUnit.DAYS;
        };
        return Duration.of(amount, chronoUnit);
    }
}