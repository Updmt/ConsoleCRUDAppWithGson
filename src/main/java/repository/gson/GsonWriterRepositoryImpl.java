package repository.gson;

import com.google.gson.reflect.TypeToken;
import model.Status;
import model.Writer;
import repository.AbstractFileRepository;

import java.util.List;

public class GsonWriterRepositoryImpl extends AbstractFileRepository<Writer> implements repository.WriterRepository {

    private static final String DEFAULT_FILE_NAME = "src/main/resources/writers.json";
    IdRepository writerIdRepository = new IdRepository("src/main/resources/writers_id.txt");

    public GsonWriterRepositoryImpl() {
        super(DEFAULT_FILE_NAME, new TypeToken<List<Writer>>() {}.getType());
    }

    @Override
    public Writer save(Writer writer) {
        List<Writer> writersList = getAll();
        writer.setId(writerIdRepository.createId());
        writersList.add(writer);
        writeListToFile(writersList);
        return writer;
    }

    @Override
    public Writer getById(Long id) {
        return getAll().stream()
                .filter(w -> w.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Writer> getAll() {
        return getAllItemsInternal();
    }

    @Override
    public Writer update(Writer writerToUpdate) {
        List<Writer> currentWriterList = getAll()
                .stream()
                .map(existingWriter -> {
                    if (existingWriter.getId().equals(writerToUpdate.getId())) {
                        return writerToUpdate;
                    } else {
                        return existingWriter;
                    }
                })
                .toList();
        writeListToFile(currentWriterList);
        return writerToUpdate;
    }

    @Override
    public void deleteById(Long writerIdToDelete) {
        List<Writer> writersList = getAll()
                .stream()
                .map(existingWriter -> {
                   if (existingWriter.getId().equals(writerIdToDelete)) {
                       existingWriter.setStatus(Status.DELETED);
                   } return existingWriter;
                })
                .toList();
        writeListToFile(writersList);
    }
}
