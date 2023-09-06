package utils;

import repository.Identify;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

public class Utils {

    public static void createFileIfNotExists(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getCurrentTime() {
        return Instant.now().toString();
    }

    public static <T extends Identify<ID>, ID> int findIndexInList(List<T> list, T entity) {
        return IntStream.range(0, list.size())
                .filter(i -> list.get(i).getId().equals(entity.getId()))
                .findFirst()
                .orElse(-1);
    }
}
