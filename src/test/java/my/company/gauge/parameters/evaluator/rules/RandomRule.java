package my.company.gauge.parameters.evaluator.rules;

import my.company.env.Configuration;
import my.company.utils.PasswordGenerator;
import org.aeonbits.owner.ConfigCache;
import org.apache.commons.lang3.RandomStringUtils;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;

import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;
import static io.vavr.API.*;
import static java.lang.Integer.parseInt;
import static my.company.utils.StringUtils.containsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class RandomRule implements RuleStrategy {
    private static final Configuration CONFIG = ConfigCache.getOrCreate(Configuration.class, System.getenv());
    private static final String LEFT_PARENTHESIS = "(";
    private static final String RIGHT_PARENTHESIS = ")";

    private static String previousGeneratedValue;

    @Override
    public String evaluate(String rule) {
        if (isNullOrEmpty(rule)) {
            throw new IllegalArgumentException("Rule for random generation can't be empty or null.");
        }

        RndReturnType type = Match(rule).of(
                Case($(containsIgnoreCase("alphanumeric")), RndReturnType.ALPHANUMERIC),
                Case($(containsIgnoreCase("alphabetic")), RndReturnType.ALPHABETIC),
                Case($(containsIgnoreCase("numeric")), RndReturnType.NUMERIC),
                Case($(containsIgnoreCase("password")), RndReturnType.PASSWORD),
                Case($(containsIgnoreCase("previous_generated_value")), RndReturnType.PREVIOUS_GENERATED_VALUE),
                Case($(), RndReturnType.UNKNOWN)
        );

        return switch (type) {
            case ALPHANUMERIC -> {
                int length = parseInt(substringBetween(rule, LEFT_PARENTHESIS, RIGHT_PARENTHESIS));
                yield cacheGeneratedValue(getRandomAlphanumeric(length));
            }
            case ALPHABETIC -> {
                int length = parseInt(substringBetween(rule, LEFT_PARENTHESIS, RIGHT_PARENTHESIS));
                yield cacheGeneratedValue(getRandomAlphabetic(length));
            }
            case NUMERIC -> {
                List<Integer> range = Arrays.stream(substringBetween(rule, LEFT_PARENTHESIS, RIGHT_PARENTHESIS).split(","))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .toList();
                yield cacheGeneratedValue(getRandomNumberInRange(range.get(0), range.get(1)));
            }
            case PASSWORD -> cacheGeneratedValue(PasswordGenerator.generate());
            case PREVIOUS_GENERATED_VALUE -> previousGeneratedValue;
            case UNKNOWN -> throw new IllegalArgumentException("Illegal rule syntax.");
        };
    }

    private static String getRandomAlphanumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    private static String getRandomAlphabetic(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    private static String getRandomNumberInRange(long min, long max) {
        return new LongRangeRandomizer(min, max).getRandomValue().toString();
    }

    private String cacheGeneratedValue(String generatedValue) {
        previousGeneratedValue = generatedValue;
        return generatedValue;
    }

    private enum RndReturnType {
        ALPHANUMERIC,
        ALPHABETIC,
        NUMERIC,
        PASSWORD,
        PREVIOUS_GENERATED_VALUE,
        UNKNOWN
    }
}
