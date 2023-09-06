package controller;

import model.*;
import repository.IdRepository;
import repository.PostRepository;
import repository.WriterRepository;
import utils.Utils;
import view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static utils.Utils.getCurrentTime;

public class PostController {
    private final View view;
    private final PostRepository postRepository;
    private final WriterRepository writerRepository;
    private final IdRepository postIdRepository;
    private final LabelController labelController;

    public PostController(View view, PostRepository postRepository, IdRepository postIdRepository,
                          WriterRepository writerRepository, LabelController labelController) {
        this.view = view;
        this.postRepository = postRepository;
        this.postIdRepository = postIdRepository;
        this.writerRepository = writerRepository;
        this.labelController = labelController;
    }

    public void createPost() {
        String writerId = view.getInput("Введите id писателя, у которого будет пост: ");
        Writer writer = writerRepository.getById(Long.valueOf(writerId));
        if (Objects.isNull(writer) || writer.getStatus() == Status.DELETED) {
            view.displayMessage("Данного писателя не существует или писатель был удален");
        } else {
            createAndSavePost();
        }
    }

    public Post createAndSavePost() {
        String content = view.getInput("Введите сообщение поста: ");
        String createTagOrNot = view.getInput("Хотите создать тег сейчас?");
        List<Label> labelList = new ArrayList<>();
        if (createTagOrNot.equalsIgnoreCase("да")) {
            while (true) {
                String stop = view.getInput("Если хотите остановить создание тегов, напишите 'стоп', если продолжить - 'создать': ");
                if (stop.equalsIgnoreCase("стоп")) {
                    break;
                } else {
                    Label label = labelController.createAndSaveLabel();
                    labelList.add(label);
                }
            }
        }
        Post post = Post.builder()
                .id(postIdRepository.createId())
                .content(content)
                .created(getCurrentTime())
                .updated(getCurrentTime())
                .postStatus(PostStatus.ACTIVE)
                .labels(labelList)
                .build();
        postRepository.save(post);
        view.displayObject(post);
        return post;
    }

    public void getAllPosts() {
        List<Post> postList = postRepository.getAll();
        if (postList.isEmpty()) {
            view.displayMessage("Писатели еще не существуют");
        } else {
            view.displayAllObjects(postList);
        }
    }

    public void getPost() {
        String updatePostSpecificWriter = view.getInput("Вы хотите прочитать пост у конкретного писателя (да или нет)? ");

        if (updatePostSpecificWriter.equalsIgnoreCase("да")) {
            String writerId = view.getInput("Введите id писателя, пост которого хотите прочесть: ");
            Writer writer = writerRepository.getById(Long.valueOf(writerId));

            if (Objects.isNull(writer) || writer.getStatus() == Status.DELETED) {
                view.displayMessage("Данного писателя не существует или писатель был удален");
            } else {
                String postId = view.getInput("Введите id поста, который хотите прочитать: ");

                Post post = postRepository.findPostByIdFromWriter(writer.getPosts(), postId);

                if (Objects.isNull(post) || post.getPostStatus() == PostStatus.DELETED) {
                    view.displayMessage("Данного поста у писателя не существует или пост был удален");
                } else {
                    view.displayObject(post);
                }
            }
        } else {
            String postId = view.getInput("Введите id поста, который хотите прочитать: ");
            Post post = postRepository.getById(Long.valueOf(postId));
            view.displayObject(post);
        }
    }

    public void updatePost() {
        String writerId = view.getInput("Введите id писателя, у которого хотите обновить пост: ");
        Writer writer = writerRepository.getById(Long.valueOf(writerId));

        if (Objects.isNull(writer) || writer.getStatus() == Status.DELETED) {
            view.displayMessage("Данного писателя не существует или писатель был удален");
        } else {
            processPostUpdate(writer);
        }
    }

    public Post processPostUpdate(Writer writer) {
        String postId = view.getInput("Введите id поста, который хотите обновить: ");

        Post post = postRepository.findPostByIdFromWriter(writer.getPosts(), postId);
        if (Objects.isNull(post) || post.getPostStatus() == PostStatus.DELETED) {
            view.displayMessage("Данного поста у писателя не существует или пост был удален");
        } else {
            String updatedField = view.getInput("Что хотите обновить (текст поста, добавить тег, обновить тег, удалить тег)?");

            post.setPostStatus(PostStatus.UNDER_REVIEW);

            List<Label> labelList = post.getLabels();
            if (updatedField.equals("текст поста")) {
                String text = view.getInput("Введите новый текст писателя: ");
                post.setContent(text);
                post.setUpdated(getCurrentTime());
            }
            if (updatedField.equals("добавить тег")) {
                Label label = labelController.createAndSaveLabel();
                labelList.add(label);
                post.setLabels(labelList);
            }
            if (updatedField.equals("обновить тег")) {
                Label updatedLabel = labelController.processLabelUpdate(post);
                int postIndex = Utils.findIndexInList(labelList, updatedLabel);
                labelList.set(postIndex, updatedLabel);
            }
            if (updatedField.equals("удалить тег")) {
                Label deletedLabel = labelController.processLabelDelete(post);
                int postIndex = Utils.findIndexInList(labelList, deletedLabel);
                labelList.set(postIndex, deletedLabel);
            }
            postRepository.update(post);
            view.displayObject(post);
        }
        return post;
    }

    public void deletePost() {
        String writerId = view.getInput("Введите id писателя, у которого хотите удалить пост: ");
        Writer writer = writerRepository.getById(Long.valueOf(writerId));

        if (Objects.isNull(writer) || writer.getStatus() == Status.DELETED) {
            view.displayMessage("Данного писателя не существует или писатель был удален");
        } else {
            processPoseDelete(writer);
        }
    }

    public Post processPoseDelete(Writer writer) {
        String postId = view.getInput("Введите id поста, который хотите удалить: ");

        Post post = postRepository.findPostByIdFromWriter(writer.getPosts(), postId);

        if (Objects.isNull(post) || post.getPostStatus() == PostStatus.DELETED) {
            view.displayMessage("Данного поста у писателя не существует или пост уже был удален");
        } else {
            post = postRepository.deleteById(post.getId());
            view.displayMessage("Пост удален");
        }
        return post;
    }
}
