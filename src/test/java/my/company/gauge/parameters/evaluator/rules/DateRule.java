package my.company.gauge.parameters.evaluator.rules;

import my.company.enums.DeicticTemporal;
import my.company.utils.dates.DateTimeOperation;
import org.apache.commons.lang3.StringUtils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static com.google.common.base.Strings.isNullOrEmpty;
import static io.vavr.API.*;
import static my.company.enums.DeicticTemporal.*;
import static my.company.utils.StringUtils.containsIgnoreCase;
import static my.company.utils.dates.Sign.ADD;
import static my.company.utils.dates.Sign.SUBTRACT;

public class DateRule implements RuleStrategy {

    private static final String LEFT_CURLY_BRACKET = "{";
    private static final String RIGHT_CURLY_BRACKET = "}";

    @Override
    public String evaluate(String rule) {
        if (isNullOrEmpty(rule)) {
            throw new IllegalArgumentException("Rule for date converting can't be empty or null.");
        }

        // initiate deictic temporal using pattern matching
        DeicticTemporal type = Match(rule).of(
                Case($(containsIgnoreCase(YESTERDAY.name())), YESTERDAY),
                Case($(containsIgnoreCase(TODAY.name())), TODAY),
                Case($(containsIgnoreCase(TOMORROW.name())), TOMORROW),
                Case($(), UNKNOWN)
        );

        // determinate the date time formatter
        DateTimeFormatter dateTimeFormatter = evaluateFormatterExpression(rule);

        // determinate the operation series expression
        String expression = StringUtils.substringBetween(
                rule,
                type.name(),
                LEFT_CURLY_BRACKET
        ).trim();

        return switch (type) {
            case YESTERDAY -> evaluateDateExpression(expression).minusDays(1).format(dateTimeFormatter);
            case TODAY -> evaluateDateExpression(expression).format(dateTimeFormatter);
            case TOMORROW -> evaluateDateExpression(expression).plusDays(1).format(dateTimeFormatter);
            case UNKNOWN -> throw new IllegalArgumentException("Illegal rule syntax.");
        };
    }

    private DateTimeFormatter evaluateFormatterExpression(String expression) {
        final String formatterAsString = StringUtils.substringBetween(
                expression,
                LEFT_CURLY_BRACKET,
                RIGHT_CURLY_BRACKET
        );

        if (!isNullOrEmpty(formatterAsString)) {
            try {
                return DateTimeFormatter.ofPattern(formatterAsString);
            } catch (Exception ignore) {
            }
        }
        return DateTimeFormatter.ISO_LOCAL_DATE;
    }

    private OffsetDateTime evaluateDateExpression(String expression) {
        return (expression.startsWith(ADD.getOperation()) || expression.startsWith(SUBTRACT.getOperation()))
                ? DateTimeOperation.addToOrSubtractFromNow(expression)
                : OffsetDateTime.now(ZoneOffset.UTC);
    }
}