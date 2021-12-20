package my.company.utils.dates;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static my.company.utils.NumberUtils.extractNumberBetweenBrackets;
import static my.company.utils.dates.Sign.ADD;

public final class DateTimeOperation {
    public static final DateTimeFormatter ISO_OFFSET_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS xxx");
    private static final String DELIMITER = " ";

    private DateTimeOperation() {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }

    public static OffsetDateTime addToOrSubtractFromNow(String exp) {
        OffsetDateTime resultDate = OffsetDateTime.now(ZoneOffset.UTC);

        if (exp.equals("0")) {
            return resultDate;
        }

        List<Operation> operations = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(exp, DELIMITER);
        while (stringTokenizer.hasMoreTokens()) {
            operations.add(new Operation(stringTokenizer.nextToken().trim()));
        }

        for (Operation operation : operations) {
            resultDate = switch (operation.getTimeUnit()) {
                case DAY -> operation.getSign().equals(ADD)
                        ? resultDate.plusDays(operation.getValue())
                        : resultDate.minusDays(operation.getValue());

                case MONTH -> operation.getSign().equals(ADD)
                        ? resultDate.plusMonths(operation.getValue())
                        : resultDate.minusMonths(operation.getValue());

                case YEAR -> operation.getSign().equals(ADD)
                        ? resultDate.plusYears(operation.getValue())
                        : resultDate.minusYears(operation.getValue());
            };
        }

        return resultDate;
    }

    /**
     * Return current LocalDateTime instance as string yyyy-MM-ddTHH:mm:ss
     *
     * @return - LocalDateTime instance as string yyyy-MM-ddTHH:mm:ss
     */
    public static String getCurrentLocalDateTimeAsStringFormattedAs(DateTimeFormatter formatter) {
        return LocalDateTime.now().format(formatter);
    }

    /**
     * Calculate local date object getting current local date plus one day plus the extracted int number
     * calculated by @see {@link my.company.utils.NumberUtils#extractNumberBetweenBrackets(String)}
     *
     * @param expression - string expression, ex: {3}, tomorrow + {2}, ...
     * @return LocalDate object calculated as now() + 1 day + days extracted from integer in brackets
     */
    public static LocalDate addToTomorrowDaysFromExpression(String expression) {
        return LocalDate.now().plusDays(1 + extractNumberBetweenBrackets(expression));
    }

    public static OffsetDateTime convertStringToOffsetDateTime(String date) {
        return OffsetDateTime.parse(date, ISO_OFFSET_DATE_TIME_FORMATTER);
    }
}
