package my.company.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public final class PasswordGenerator {

    private PasswordGenerator() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String generate() {
        String upperCaseLetters = RandomStringUtils.random(1, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(5, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(5);
        String specialChar = RandomStringUtils.random(1, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);

        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);

        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(chr -> (char) chr)
                .collect(toList());

        Collections.shuffle(pwdChars);

        return pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
