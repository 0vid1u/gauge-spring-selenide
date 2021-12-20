package my.company.utils.dates;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeUnit {
    DAY("d"),
    MONTH("m"),
    YEAR("y");

    private final String unit;
}
