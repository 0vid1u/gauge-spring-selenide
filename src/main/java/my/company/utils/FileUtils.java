package my.company.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermission;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.nio.file.Files.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.io.FileUtils.deleteDirectory;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Slf4j
public final class FileUtils {

    private FileUtils() {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }

    /**
     * Check if file exist
     *
     * @param pathAndFileName Path to file
     * @return Return true if file exists
     */
    public static boolean fileExist(String pathAndFileName) {
        log.info("Check if file exist: " + pathAndFileName);
        File findFile = new File(pathAndFileName);
        return findFile.isFile();
    }

    /**
     * Check if file not exist
     *
     * @param pathAndFileName Path to file
     * @return Return true if file exists
     */
    public static boolean isNotFile(String pathAndFileName) {
        log.info("Check if file not exist: " + pathAndFileName);
        File findFile = new File(pathAndFileName);
        return !findFile.isFile();
    }

    /**
     * Check if file is directory
     *
     * @param path Directory path
     * @return Return true if path point to a directory
     */
    public static boolean isDirectory(String path) {
        return new File(path).isDirectory();
    }

    /**
     * Check if file is not directory
     *
     * @param path Directory path
     * @return Return false if path point to a directory
     */
    public static boolean isNotDirectory(String path) {
        return !new File(path).isDirectory();
    }

    /**
     * Append string to file
     *
     * @param filePath   File path
     * @param appendText String to append
     */
    public static void appendStringToFile(String filePath, String appendText) {
        try (FileWriter fileWriter = new FileWriter(filePath, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            printWriter.println(appendText);
        } catch (IOException e) {
            log.error("IOException: " + e.getMessage(), e);
        }

    }

    /**
     * Read file content
     *
     * @param file Path to file
     * @return File content in string format
     */
    public static String readFile(String file) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(currentLine).append(System.lineSeparator());
            }
        } catch (IOException e) {
            log.error("IOException: " + e.getMessage(), e);
        }
        return stringBuilder.toString();
    }

    /**
     * Clear file content
     *
     * @param file Path to file
     */
    public static void clearFile(String file) {
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            fileWriter.write(EMPTY);
        } catch (IOException e) {
            log.error("IOException: " + e.getMessage(), e);
        }
    }

    /**
     * Write line to file
     *
     * @param path Path to file
     * @param line Line to write
     */
    public static void writeLineToFile(String path, String line) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)))) {
            out.println(line);
        } catch (IOException e) {
            log.error("IOException: " + e.getMessage(), e);
        }
    }

    public static <T> void writeObjectToFile(String path, T obj) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(obj);
        } catch (IOException e) {
            log.error("IOException: " + e.getMessage(), e);
        }
    }

    public static <T> T readObjectFromFile(String path) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            //noinspection unchecked
            return (T) in.readObject();
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage(), e);
        }

        return null;
    }

    /**
     * Move file to destination
     *
     * @param source source file path
     * @param target destination file path
     */
    public static void moveFile(String source, String target) {
        try {
            Path sourcePath = Paths.get(source);
            Path targetPath = Paths.get(target);
            targetPath = targetPath.resolve(sourcePath.getFileName());

            move(sourcePath, targetPath);
            log.info("File : " + source + " moved to " + target);
        } catch (FileAlreadyExistsException fae) {
            log.error("FileAlreadyExistsException: " + fae.getMessage(), fae);
        } catch (IOException e) {
            log.error("IOException: " + e.getMessage(), e);
        }
    }

    /**
     * Move file to destination with a new name
     *
     * @param source      source file path
     * @param target      destination file path
     * @param newFileName new file name
     * @return path to moved file
     */
    public static Path moveFileUsingNewFileName(Path source, Path target, String newFileName) {
        try {
            Path targetPath = move(source, target.resolve(newFileName), REPLACE_EXISTING);
            log.info("File : " + source + " moved to " + target);
            return targetPath;
        } catch (IOException e) {
            log.error("IOException: " + e.getMessage(), e);
        }

        return null;
    }

    /**
     * Clone file to destination with a new name
     *
     * @param source      source file path
     * @param target      destination file path
     * @param newFileName new file name
     * @return path to cloned file
     */
    public static Path cloneFileUsingNewFileName(Path source, Path target, String newFileName) {
        try {
            Path targetPath = copy(source, target.resolve(newFileName), REPLACE_EXISTING);
            log.info("File : " + source + " cloned to " + target);
            return targetPath;
        } catch (IOException e) {
            log.error("IOException: " + e.getMessage(), e);
        }

        return null;
    }

    /**
     * Delete file from path
     *
     * @param pathToDelete path to file
     */
    public static void deleteFile(Path pathToDelete) {
        if (!exists(pathToDelete, LinkOption.NOFOLLOW_LINKS)) {
            return;
        }

        try {
            if (Files.isDirectory(pathToDelete)) {
                deleteDirectory(pathToDelete.toFile());
            } else {
                delete(pathToDelete);
            }
        } catch (IOException e) {
            log.error("IOException: " + e.getMessage(), e);
        }
    }

    /**
     * Return file size
     *
     * @param file Path to file
     * @return File size
     * @throws FileNotFoundException File not found exception
     */
    public static long fileSize(Path file) throws FileNotFoundException {
        if (exists(file, LinkOption.NOFOLLOW_LINKS)) {
            try {
                return size(file);
            } catch (IOException io) {
                log.error("Error getting file size for " + file.toString(), io);
                throw new RuntimeException(io);
            }
        }
        throw new FileNotFoundException(file.toString());
    }

    /**
     * Return list of files filtered by extension
     *
     * @param directory path to directory
     * @param extension file extension, ex: .xml
     * @return List of files
     */
    public static Optional<List<String>> getFilesByExtension(String directory, String extension) {
        Optional<List<String>> fileList;
        try (Stream<Path> stream = list(Paths.get(directory))) {
            fileList = Optional.of(stream
                    .map(String::valueOf)
                    .filter(path -> path.endsWith(extension))
                    .toList());

        } catch (IOException e) {
            fileList = Optional.empty();
            log.error("IOException: " + e.getMessage(), e);
        }
        return fileList;
    }

    /**
     * Get first file path from list of files filtered by extension
     *
     * @param directory Path to directory
     * @param extension File extension
     * @return Path to first found file
     */
    public static Optional<String> getFirstFoundFilePathByExtension(String directory, String extension) {
        Optional<List<String>> files = getFilesByExtension(directory, extension);
        return files.flatMap(strings -> strings.stream().findFirst());
    }

    /**
     * Returns formatted permissions for current file.
     *
     * @param path Current file.
     * @return Formatted permissions.
     */
    public static String getPermissions(Path path) {
        char directory = '-';
        if (Files.isDirectory(path))
            directory = 'd';

        char readable = '-';
        if (isReadable(path))
            readable = 'r';

        char writeable = '-';
        if (isWritable(path))
            writeable = 'w';

        char executable = '-';
        if (isExecutable(path))
            executable = 'x';

        return String.valueOf(directory) + readable +
               writeable + executable;
    }

    /**
     * Get file as byte array
     *
     * @param file file
     * @return byte[]
     */
    public static byte[] readBytesFromFile(File file) {
        byte[] bytesArray = null;

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            bytesArray = new byte[(int) file.length()];
            bytesArray = IOUtils.toByteArray(fileInputStream);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return bytesArray;
    }

    public static void setPermissions(File file, Set<PosixFilePermission> permissions) {
        try {
            if (file.exists()) {
                log.debug("Is Execute allow : " + file.canExecute());
                log.debug("Is Write allow : " + file.canWrite());
                log.debug("Is Read allow : " + file.canRead());
            }

            if (!SystemUtils.IS_OS_WINDOWS) {
                log.debug("Non Windows OS detected: " + SystemUtils.OS_NAME + SystemUtils.OS_ARCH + SystemUtils.OS_VERSION);
                setPosixFilePermissions(file.getAbsoluteFile().toPath(), permissions);
            }

            log.debug("Is Execute allow : " + file.canExecute());
            log.debug("Is Write allow : " + file.canWrite());
            log.debug("Is Read allow : " + file.canRead());

            if (file.createNewFile()) {
                log.debug("File is created!");
            } else {
                log.debug("File already exists.");
            }

        } catch (IOException e) {
            log.error("Unable to set file permissions: \n" + e.getMessage());
        }
    }

    @SneakyThrows
    public static Path generateTimelinePath(String firstPath) {
        final LocalDateTime now = LocalDateTime.now();
        final String dateAsString = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        final String timeAsString = now.format(DateTimeFormatter.ofPattern("HH_mm_ss"));

        Path path = Path.of(firstPath, dateAsString, timeAsString);
        createDirectories(path);
        return path;
    }
}