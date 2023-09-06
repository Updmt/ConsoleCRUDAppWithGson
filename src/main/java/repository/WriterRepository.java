package repository;

import com.google.gson.reflect.TypeToken;
import model.Status;
import model.Writer;
import utils.Utils;

import java.util.List;

public class WriterRepository extends AbstractFileRepository<Writer, Long> {

    private static final String DEFAULT_FILE_NAME = "writers.json";

    public WriterRepository() {
        super(DEFAULT_FILE_NAME, new TypeToken<List<Writer>>() {}.getType());
    }

    @Override
    public void save(Writer writer) {
        List<Writer> writersList = getAll();
        writersList.add(writer);
        writeListToFile(writersList);
    }

    @Override
    public Writer getById(Long id) {
        return getAll().stream()
                .filter(w -> w.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Writer update(Writer writer) {
        List<Writer> writersList = getAll();
        int index = Utils.findIndexInList(writersList, writer);
        writersList.set(index, writer);
        writeListToFile(writersList);
        return writer;
    }

    @Override
    public Writer deleteById(Long id) {
        List<Writer> writersList = getAll();
        Writer writer = getById(id);
        int index = Utils.findIndexInList(writersList, writer);
        writer.setStatus(Status.DELETED);
        writersList.set(index, writer);
        writeListToFile(writersList);
        return writer;
    }
}
