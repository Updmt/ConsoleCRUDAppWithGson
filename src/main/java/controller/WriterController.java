package controller;

import model.Post;
import model.Status;
import model.Writer;
import repository.WriterRepository;
import repository.gson.GsonWriterRepositoryImpl;

import java.util.List;

public class WriterController {
    private final WriterRepository writerRepository = new GsonWriterRepositoryImpl();

    public Writer createWriter(String firstName, String lastName, List<Post> postList) {
        Writer writer = Writer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .posts(postList)
                .status(Status.CREATED)
                .build();
        return writerRepository.save(writer);
    }

    public List<Writer> getAllWriters() {
        return writerRepository.getAll();
    }

    public Writer getWriter(String writerId) {
        return writerRepository.getById(Long.valueOf(writerId));
    }

    public Writer updateWriter(String writerId, String firstName, String lastName) {
        Writer writer = getWriter(writerId);
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        writer.setStatus(Status.UPDATED);
        return writerRepository.update(writer);
    }

    public void deleteWriter(String writerId) {
        writerRepository.deleteById(Long.valueOf(writerId));
    }

}
