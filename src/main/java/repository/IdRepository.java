package repository;

import utils.Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class IdRepository {

    private final Path path;

    public IdRepository(String fileName) {
        this.path = Paths.get(fileName);
        Utils.createFileIfNotExists(path);
    }

    public long createId() {
        long id = 0;
        try {
            String content = Files.readString(path, StandardCharsets.UTF_8);
            if (!content.isEmpty()) {
                id = Long.parseLong(content);
            }
            Files.writeString(path, String.valueOf(id + 1), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
}
