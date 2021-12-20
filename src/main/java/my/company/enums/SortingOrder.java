package my.company.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import my.company.extensions.assertj.CaseInsensitiveStringComparator;

import java.util.Comparator;

@Getter
@AllArgsConstructor
public enum SortingOrder {
    ASCENDING(Comparator.naturalOrder(), new CaseInsensitiveStringComparator()),
    DESCENDING(Comparator.reverseOrder(), new CaseInsensitiveStringComparator().reversed());

    private final Comparator<String> stringComparator;
    private final Comparator<String> stringCaseInsensitiveStringComparator;
}
