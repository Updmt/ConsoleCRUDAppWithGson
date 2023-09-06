package controller;

import model.Post;
import model.Status;
import model.Writer;
import repository.IdRepository;
import repository.WriterRepository;
import utils.Utils;
import view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WriterController {
    private final View view;
    private final WriterRepository writerRepository;
    private final PostController postController;
    private final IdRepository writterIdRepository;

    public WriterController(View view, WriterRepository writerRepository, PostController postController, IdRepository writterIdRepository) {
        this.view = view;
        this.writerRepository = writerRepository;
        this.postController = postController;
        this.writterIdRepository = writterIdRepository;
    }

    public void createWriter() {
        String firstName = view.getInput("Введите имя писателя: ");
        String lastName = view.getInput("Введите фамилию писателя: ");
        String createPostOrNot = view.getInput("Хотите создать пост сейчас?");
        List<Post> postList = new ArrayList<>();
        if (createPostOrNot.equalsIgnoreCase("да")) {
            while (true) {
                String stop = view.getInput("Если хотите остановить создание постов, напишите 'стоп', если продолжить - 'создать': ");
                if (stop.equalsIgnoreCase("стоп")) {
                    break;
                } else {
                    Post post = postController.createAndSavePost();
                    postList.add(post);
                }
            }
        }
        Writer writer = Writer.builder()
                .id(writterIdRepository.createId())
                .firstName(firstName)
                .lastName(lastName)
                .posts(postList)
                .status(Status.CREATED).build();
        writerRepository.save(writer);
        view.displayObject(writer);
    }

    public void getWriter() {
        String id = view.getInput("Введите id писателя, которого хотите получить: ");
        Writer writer = writerRepository.getById(Long.parseLong(id));
        if (Objects.isNull(writer)) {
            view.displayMessage("Такого писателя не существует");
        } else {
            view.displayObject(writer);
        }
    }

    public void getAllWriters() {
        List<Writer> writerList = writerRepository.getAll();
        if (writerList.isEmpty()) {
            view.displayMessage("Писатели еще не существуют");
        } else {
            view.displayAllObjects(writerList);
        }
    }

    public void updateWriter() {
        String id = view.getInput("Введите id писателя, которого хотите обновить: ");
        Writer writer = writerRepository.getById(Long.parseLong(id));
        if (Objects.isNull(writer) || writer.getStatus() == Status.DELETED) {
            view.displayMessage("Такого писателя не существует или писатель был удален");
        } else {
            String updatedField = view.getInput("Что хотите обновить (имя, фамилия, добавить пост, обновить пост, удалить пост)?");
            writer.setStatus(Status.UPDATED);
            List<Post> postList = writer.getPosts();
            if (updatedField.equals("имя")) {
                String name = view.getInput("Введите имя писателя: ");
                writer.setFirstName(name);
            } else if (updatedField.equals("фамилия")) {
                String lastName = view.getInput("Введите фамилию писателя: ");
                writer.setLastName(lastName);
            } else if (updatedField.equals("добавить пост")) {
                postList.add(postController.createAndSavePost());
                writer.setPosts(postList);
            } else if (updatedField.equals("обновить пост")) {
                Post updatedPost = postController.processPostUpdate(writer);
                int postIndex = Utils.findIndexInList(postList, updatedPost);
                postList.set(postIndex, updatedPost);
            } else if (updatedField.equals("удалить пост")) {
                Post postToDelete = postController.processPoseDelete(writer);
                int postIndex = Utils.findIndexInList(postList, postToDelete);
                postList.set(postIndex, postToDelete);
            }
            writerRepository.update(writer);
            view.displayObject(writer);
        }
    }

    public void deleteWriter() {
        String id = view.getInput("Введите id писателя, которого хотите удалить: ");
        Writer writer = writerRepository.getById(Long.parseLong(id));
        if (Objects.isNull(writer)|| writer.getStatus() == Status.DELETED) {
            view.displayMessage("Такого писателя не существует или он уже был удален");
        } else {
            writerRepository.deleteById(writer.getId());
            view.displayMessage("Писатель удален");
        }
    }
}
