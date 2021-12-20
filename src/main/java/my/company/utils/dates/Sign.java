package my.company.utils.dates;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sign {
    ADD("+"),
    SUBTRACT("-");

    private final String operation;
}
