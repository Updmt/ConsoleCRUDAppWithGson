package controller;

import model.*;
import repository.IdRepository;
import repository.LabelRepository;
import repository.PostRepository;
import repository.WriterRepository;
import view.View;

import java.util.List;
import java.util.Objects;


public class LabelController {

    private final View view;
    private final WriterRepository writerRepository;
    private final IdRepository labelIdRepository;
    private final LabelRepository labelRepository;
    private final PostRepository postRepository;

    public LabelController(View view, WriterRepository writerRepository,
                           IdRepository labelIdRepository, LabelRepository labelRepository, PostRepository postRepository) {
        this.view = view;
        this.writerRepository = writerRepository;
        this.labelIdRepository = labelIdRepository;
        this.labelRepository = labelRepository;
        this.postRepository = postRepository;
    }

    public void createLabel() {
        String writerId = view.getInput("Введите id писателя, у поста которого будет тег: ");
        Writer writer = writerRepository.getById(Long.valueOf(writerId));
        view.displayObject(writer);
        if (Objects.isNull(writer) || writer.getStatus() == Status.DELETED) {
            view.displayMessage("Данного писателя не существует или писатель был удален");
        } else {
            String postId = view.getInput("Введите id поста, у которого будет тег: ");
            Post post = postRepository.getById(Long.valueOf(postId));
            if (Objects.isNull(post) || post.getPostStatus() == PostStatus.DELETED) {
                view.displayMessage("Данного поста не существует или пост был удален");
            } else {
                view.displayObject(post);
                createAndSaveLabel();
            }
        }
    }

    public void getLabel() {
        String updatePostSpecificWriter = view.getInput("Вы хотите прочитать тег конкретного поста (да или нет)? ");

        if (updatePostSpecificWriter.equalsIgnoreCase("да")) {
            String writerId = view.getInput("Введите id поста, тег которого хотите прочесть: ");
            Post post = postRepository.getById(Long.valueOf(writerId));

            if (Objects.isNull(post) || post.getPostStatus() == PostStatus.DELETED) {
                view.displayMessage("Данного поста не существует или пост был удален");
            } else {
                String labelId = view.getInput("Введите id тега, который хотите прочитать: ");

                Label label = labelRepository.findLabelByIdFromPost(post.getLabels(), labelId);

                if (Objects.isNull(label) || label.getStatus() == Status.DELETED) {
                    view.displayMessage("Данного тега у поста не существует или тег был удален");
                } else {
                    view.displayObject(label);
                }
            }
        } else {
            String postId = view.getInput("Введите id тега, который хотите прочитать: ");
            Label label = labelRepository.getById(Long.valueOf(postId));
            view.displayObject(label);
        }
    }

    public void getAllLabels() {
        List<Label> labelList = labelRepository.getAll();
        if (labelList.isEmpty()) {
            view.displayMessage("Писатели еще не существуют");
        } else {
            view.displayAllObjects(labelList);
        }
    }

    public void updateLabel() {
        String writerId = view.getInput("Введите id писателя, у которого хотите обновить тег: ");
        Writer writer = writerRepository.getById(Long.valueOf(writerId));

        if (Objects.isNull(writer) || writer.getStatus() == Status.DELETED) {
            view.displayMessage("Данного писателя не существует или писатель был удален");
        } else {
            String postId = view.getInput("Введите id поста, у которого будет тег: ");
            Post post = postRepository.getById(Long.valueOf(postId));

            if (Objects.isNull(post) || post.getPostStatus() == PostStatus.DELETED) {
                view.displayMessage("Данного поста не существует или пост был удален");
            } else {
                processLabelUpdate(post);
            }
        }
    }

    public Label processLabelUpdate(Post post) {
        String labelId = view.getInput("Введите id тега, который хотите обновить: ");
        Label label = labelRepository.findLabelByIdFromPost(post.getLabels(), labelId);
        if (Objects.isNull(label) || label.getStatus() == Status.DELETED) {
            view.displayMessage("Данного тега у поста не существует или тег был удален");
        }
        String text = view.getInput("Введите новый тег: ");
        label.setName(text);
        label.setStatus(Status.UPDATED);
        labelRepository.update(label);
        view.displayObject(label);
        return label;
    }

    public void deleteLabel() {
        String writerId = view.getInput("Введите id писателя, у которого хотите удалить тег: ");
        Writer writer = writerRepository.getById(Long.valueOf(writerId));

        if (Objects.isNull(writer) || writer.getStatus() == Status.DELETED) {
            view.displayMessage("Данного писателя не существует или писатель был удален");
        } else {
            String postId = view.getInput("Введите id поста, тег которого хотите удалить: ");
            Post post = postRepository.getById(Long.valueOf(postId));

            if (Objects.isNull(post) || post.getPostStatus() == PostStatus.DELETED) {
                view.displayMessage("Данного поста не существует или пост был удален");
            } else {
                processLabelDelete(post);
            }
        }
    }

    public Label processLabelDelete(Post post){
        String labelId = view.getInput("Введите id тега, который хотите удалить: ");

        Label label = labelRepository.findLabelByIdFromPost(post.getLabels(), labelId);

        if (Objects.isNull(label) || label.getStatus() == Status.DELETED) {
            view.displayMessage("Данного тега у поста не существует или тег был уже удален");
        } else {
            label = labelRepository.deleteById(label.getId());
            view.displayObject(label);
        }
        return label;
    }

    public Label createAndSaveLabel() {
        String content = view.getInput("Введите тег: ");
        Label label = Label.builder()
                .id(labelIdRepository.createId())
                .status(Status.CREATED)
                .name(content).build();
        labelRepository.save(label);
        return label;
    }
}
