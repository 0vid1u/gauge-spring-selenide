package my.company.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.jeasy.random.randomizers.misc.BooleanRandomizer;
import org.jeasy.random.randomizers.range.BigDecimalRangeRandomizer;
import org.jeasy.random.randomizers.range.IntegerRangeRandomizer;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;
import org.jeasy.random.randomizers.range.OffsetDateTimeRangeRandomizer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;

public final class DataFaker {
    private static final SecureRandom random = new SecureRandom();

    private static final OffsetDateTime today = OffsetDateTime.now(ZoneOffset.UTC);
    private static final OffsetDateTime startDate = OffsetDateTime.of(today.getYear() - 1, 12, 1, 0, 0, 0, 0, ZoneOffset.UTC);

    private static final List<String> FIRST_NAMES = List.of("Gino", "Rosella", "Ina", "Royce", "Eva", "Edison", "Jimmie", "Francesco", "Darrell", "Bennie");
    private static final List<String> LAST_NAMES = List.of("Williamson", "Bradshaw", "Petty", "English", "Hall", "Forbes", "Cannon", "Davies", "Browning", "Martinez");
    private static final List<String> COMPANIES = List.of("Lear", "Apple", "Humana", "Fortive", "Citigroup", "Travelers", "Mosaic", "Jabil", "MetLife", "Cintas");

    private DataFaker() {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }

    public static String getRandomFirstName() {
        return getRandomItemFromGenericList(FIRST_NAMES) + getRandomAlphabetic(2).toLowerCase();
    }

    public static String getRandomLastName() {
        return getRandomItemFromGenericList(LAST_NAMES) + getRandomAlphabetic(2).toLowerCase();
    }

    public static String getRandomCompanyName() {
        return getRandomItemFromGenericList(COMPANIES);
    }

    public static boolean getRandomBoolean() {
        return new BooleanRandomizer().getRandomValue();
    }

    public static String getRandomAlphabetic(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String getRandomAlphanumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String getRandomNumeric(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public static OffsetDateTime getRandomOffsetDateTimeInRange(OffsetDateTime dateFrom, OffsetDateTime dateTo) {
        return new OffsetDateTimeRangeRandomizer(dateFrom, dateTo).getRandomValue();
    }

    public static <T> T getRandomItemFromGenericList(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public static <T> T getRandomItemFromArray(T[] array) {
        return List.of(array).get(new Random().nextInt(array.length));
    }

    public static <T extends Enum<?>> T getRandomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static long getRandomLongInRange(long min, long max) {
        return new LongRangeRandomizer(min, max).getRandomValue();
    }

    public static int getRandomIntegerInRange(int min, int max) {
        return new IntegerRangeRandomizer(min, max).getRandomValue();
    }

    public static BigDecimal getRandomBigDecimalInRange(double min, double max) {
        return new BigDecimalRangeRandomizer(min, max)
                .getRandomValue()
                .setScale(2, RoundingMode.HALF_UP);
    }

    public static int getIntegerRandomId() {
        return getRandomIntegerInRange(1000000, 9999999);
    }

    public static OffsetDateTime getRandomOffsetDateTime() {
        return getRandomOffsetDateTimeInRange(startDate, today);
    }
}
