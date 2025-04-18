package ch.artcode.chatbot.infrastructure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Comparator;
import java.util.stream.Stream;

public class FileManagerService {

    private static final Logger log = LogManager.getLogger(FileManagerService.class);

    public static void createDirectory(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    public static void deleteDirectory(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (Files.exists(path)) {
            try (Stream<Path> paths = Files.walk(path)) {
                paths.sorted(Comparator.reverseOrder())
                        .forEach(subPath -> {
                            try {
                                Files.delete(subPath);
                            } catch (IOException e) {
                                throw new RuntimeException("Error deleting: " + subPath + " -> " + e.getMessage(), e);
                            }
                        });
            }
        }
    }

    public static void saveFileFromBase64(String base64Data, String targetDirectoryPath, String fileName) throws IOException {
        byte[] fileData = Base64.getDecoder().decode(base64Data);
        Path targetPath = Paths.get(targetDirectoryPath, fileName);
        Files.write(targetPath, fileData);
    }
}