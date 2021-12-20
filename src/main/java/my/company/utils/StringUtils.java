package my.company.utils;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class StringUtils {
    private StringUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String replaceBetween(String theString, String value, String open, String close) {
        return theString.replaceAll("(" + open + ")[^&]*(" + close + ")", "$1" + value + "$2");
    }

    public static Predicate<String> startsWith(final String begin) {
        return s -> s.startsWith(begin);
    }

    public static Predicate<String> endsWith(final String ends) {
        return s -> s.endsWith(ends);
    }

    public static Predicate<String> containsIgnoreCase(final String str) {
        return s -> org.apache.commons.lang3.StringUtils.containsIgnoreCase(s, str);
    }

    public static String convertToString(CharSequence... sequence) {
        return Arrays.stream(sequence)
                .map(CharSequence::toString)
                .collect(Collectors.joining());
    }
}