package my.company.utils;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;

public final class Stringify {

    private Stringify() {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }

    /**
     * Convert any object to json string
     *
     * @param obj object instance
     * @param <T> generic type
     * @return object as json string
     */
    public static <T> String prettyPrint(T obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return lineSeparator() + gson.toJson(obj).replaceAll("(?m)^", "\t");
    }

    /**
     * Extract file content from path as string
     *
     * @param path path to file
     * @return file content as string
     */
    public static String prettyPrint(Path path) {
        String result;
        try {
            result = Files.asCharSource(path.toFile(), StandardCharsets.UTF_8).read();
        } catch (IOException e) {
            result = format("Failed to get file content from path %s, error : %s", path, e.getMessage());
        }
        return lineSeparator() + result.replaceAll("(?m)^", "\t");
    }

    public static String join(Object[] array, CharSequence separator) {
        return (array == null) ? "null" : Arrays.stream(array)
                .map(obj -> {
                    if (obj == null) {
                        return "null";
                    } else {
                        return obj.toString();
                    }
                })
                .collect(Collectors.joining(separator));
    }
}
