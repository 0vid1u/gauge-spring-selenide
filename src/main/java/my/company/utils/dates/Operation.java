package my.company.utils.dates;

import lombok.Getter;
import my.company.utils.StringUtils;

import static io.vavr.API.*;
import static java.lang.Integer.parseInt;
import static my.company.utils.dates.Sign.ADD;
import static my.company.utils.dates.Sign.SUBTRACT;
import static org.apache.commons.lang.StringUtils.substringBetween;

@Getter
public class Operation {

    private final Sign sign;
    private final int value;
    private final TimeUnit timeUnit;

    public Operation(final String token) {
        this.sign = extractSignFromToken(token);
        this.timeUnit = extractTimeUnitFromToken(token);
        this.value = extractValueFromToken(token, this.sign, this.timeUnit);
    }

    private Sign extractSignFromToken(String token) {
        return Match(token).of(
                Case($(StringUtils.startsWith(ADD.getOperation())), ADD),
                Case($(StringUtils.startsWith(SUBTRACT.getOperation())), SUBTRACT),
                Case($(), o -> {
                    throw new IllegalArgumentException("Operation must start with +/-");
                })
        );
    }

    private int extractValueFromToken(String token, Sign sign, TimeUnit timeUnit) {
        try {
            return parseInt(substringBetween(token, sign.getOperation(), timeUnit.getUnit()));
        } catch (Exception ex) {
            throw new NumberFormatException("Could not convert " + token + " to number.");
        }
    }

    private TimeUnit extractTimeUnitFromToken(String token) {
        return Match(token).of(
                Case($(StringUtils.endsWith(TimeUnit.DAY.getUnit())), TimeUnit.DAY),
                Case($(StringUtils.endsWith(TimeUnit.MONTH.getUnit())), TimeUnit.MONTH),
                Case($(StringUtils.endsWith(TimeUnit.YEAR.getUnit())), TimeUnit.YEAR),
                Case($(), o -> {
                    throw new IllegalArgumentException("Operation must ends with d/m/y");
                })
        );
    }
}
