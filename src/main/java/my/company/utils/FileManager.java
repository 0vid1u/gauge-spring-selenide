package my.company.utils;

import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class FileManager {

    private static final String DEFAULT_EXPORT_DIRECTORY = "export-data/";

    private final String exportFilePath;

    public FileManager(String filePath, String fileName) {
        this.exportFilePath = getFilePath(filePath, fileName);
    }


    public <T> void exportToFile(T obj) {
        try {
            FileUtils.writeObjectToFile(exportFilePath, obj);
        } catch (Exception e) {
            log.error("Error", e);
        }
    }

    public <T> void writeToFile(T obj) {
        try {
            FileUtils.writeLineToFile(exportFilePath, obj.toString());
        } catch (Exception e) {
            log.error("Error", e);
        }
    }

    public <T> T importFromFile(String filePath, String fileName) {
        if (filePath == null) {
            filePath = DEFAULT_EXPORT_DIRECTORY;
        }

        filePath = Paths.get(filePath, fileName).toFile().getAbsolutePath();

        if (FileUtils.fileExist(filePath)) {
            return FileUtils.readObjectFromFile(filePath);
        }

        return null;
    }

    private String getFilePath(String filePath, String fileName) {
        if (StringUtil.isNullOrEmpty(filePath)
                || (FileUtils.isNotDirectory(filePath)
                && FileUtils.isNotFile(filePath))) {

            filePath = DEFAULT_EXPORT_DIRECTORY;
            try {
                if (FileUtils.isNotDirectory(filePath)) {
                    Files.createDirectory(Paths.get(filePath));
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        if (FileUtils.isNotFile(filePath)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String exportFileName = fileName + "-" + LocalDateTime.now().format(formatter);
            filePath = Paths.get(filePath, exportFileName).toFile().getAbsolutePath();
        }
        return filePath;
    }
}