package my.company.datastore;

import org.openqa.selenium.NoSuchElementException;

import java.util.List;

public enum Memory {
    BUFFER;

    private static final List<Memory> VALUES = List.of(values());

    public static Memory fromString(final String key) {
        return VALUES.stream()
                .filter(value -> value.name().equalsIgnoreCase(key))
                .findFirst()
                .orElseThrow(() -> {
                    throw new NoSuchElementException("No such memory key.");
                });
    }
}
