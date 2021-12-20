package my.company.utils;

import java.math.BigDecimal;

import static org.apache.commons.lang3.StringUtils.chop;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public final class NumberUtils {

    private NumberUtils() {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }

    public static double getNumber(String value) {
        if (value.matches("\\d+.\\d+\\W.") || getSymbol(value).equals("%")) {
            return Double.parseDouble(value.replaceAll("[^\\d(.\\d)*]", ""));
        }
        throw new NumberFormatException("Wrong number format");
    }

    public static String getSymbol(String value) {
        try {
            return value.replaceAll("\\d|\\s|\\.", "");
        } catch (Exception ignore) {
            return "";
        }
    }

    /**
     * Extract from String amount only number's part and convert it to BigDecimal
     * e.g.: "234,65 EUR" -> 234.65
     *
     * @param amountAsString amount as string
     * @return BigDecimal value after conversion from string
     */
    public static BigDecimal convertStringAmountToBigDecimal(String amountAsString) {
        amountAsString = amountAsString.trim().replaceAll("[a-zA-Z$€ ]", "");
        char separator = '~';
        try {
            separator = amountAsString.charAt(amountAsString.length() - 3);
        } catch (IndexOutOfBoundsException ignore) {
            //ignore
        }
        if (Character.toString(separator).equals(".")) {
            amountAsString = amountAsString.replace(",", "");
        } else if (Character.toString(separator).matches("[0-9]")) {
            amountAsString = amountAsString.replace(",", ".");
        } else {
            amountAsString = amountAsString.replace("\\.", "")
                    .replace(",", ".");
        }
        return new BigDecimal(amountAsString);
    }

    public static Integer tryParseIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long tryParseLongOrNull(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static int extractNumberBetweenBrackets(String expression) {
        if (expression.contains("{") && expression.contains("}")) {
            return Integer.parseInt(substringBetween(expression, "{", "}"));
        } else if (expression.matches("[0-9]")) {
            return Integer.parseInt(expression);
        }
        return 0;
    }

    /**
     * Extract from String amount only number's part and replace "." with ","
     * e.g.: "EUR 24.14" -> 24,14
     *       "0.19000000%" -> 0,19
     *
     * @param numberExpression amount or percent as string
     * @return String value after conversion
     */
    public static String convertAmountOrPercent(String numberExpression) {
        String numberWithoutSymbols = numberExpression.trim().replaceAll("[a-zA-Z$€% ]", "");

        String number = numberWithoutSymbols.replace("\\.", ",");
        for (int i = 0; i <= number.length(); i++) {
            if (number.endsWith("0")) {
                number = chop(number);
            } else break;
        }
        return number;
    }
}