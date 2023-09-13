package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.Utils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFileRepository<T> {

    protected final Path path;
    protected final Gson gson;
    protected final Type listType;

    protected AbstractFileRepository(String defaultFileName, Type listType) {
        this.path = Paths.get(defaultFileName);
        this.gson = new Gson();
        this.listType = listType;
        Utils.createFileIfNotExists(path);
    }

    protected List<T> getAllItemsInternal() {
        String jsonData;
        try {
            jsonData = Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<T> resultList = gson.fromJson(jsonData, listType);
        return resultList != null ? resultList : new ArrayList<>();
    }

    protected void writeListToFile(List<T> list) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonData = gson.toJson(list);
        try {
            Files.writeString(path, jsonData, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
