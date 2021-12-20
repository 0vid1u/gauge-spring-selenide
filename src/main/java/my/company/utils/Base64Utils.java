package my.company.utils;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Base64;

public final class Base64Utils {

    private Base64Utils() {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }

    public static void base64ToFile(String path, String base64String) throws IOException {
        File file = new File(path);
        byte[] bytes = Base64.getDecoder().decode(base64String);
        FileUtils.writeByteArrayToFile(file, bytes);
    }

    public static String getBase64Line(InputStream stream) throws Exception {
        InputStreamReader isr = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder out = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.contains("downloadFile")) {
                out.append(line);
                line = out.toString();
                return line;
            }
        }
        return null;
    }

    public static void decode(String base64, String targetFile) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        writeByteArraysToFile(targetFile, decodedBytes);
    }

    public static void writeByteArraysToFile(String fileName, byte[] content) throws IOException {
        File file = new File(fileName);
        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file))) {
            writer.write(content);
            writer.flush();
        }
    }
}