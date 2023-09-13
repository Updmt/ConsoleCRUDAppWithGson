package view;

import controller.LabelController;
import controller.PostController;
import model.Label;
import model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostView {
    private final Scanner scanner = new Scanner(System.in);
    private final PostController postController = new PostController();

    //TODO: Это нормально или лучше передавать в конструкторе, чтоб не создавать несколько раз тут и в LabelView?
    private final LabelController labelController = new LabelController();

    public void createPost() {
        System.out.println("Введите сообщение поста: ");
        String content = scanner.nextLine();
        List<Label> labelList = new ArrayList<>();
        while (true) {
            System.out.println("Введите айди лейбла, который хотите добавить к посту. Стоп - для остановки");
            String labelId = scanner.nextLine();
            if (labelId.equalsIgnoreCase("стоп")) {
                break;
            } else {
                Label label = labelController.getLabel(labelId);
                labelList.add(label);
            }
        }
        Post createdPost = postController.createPost(content, labelList);
        System.out.println("Созданный пост: " + createdPost);
    }

    public void getAllPosts() {
        System.out.println("Все посты: " + postController.getAllPosts());
    }

    public void getPost() {
        System.out.println("Введите айди поста, который хотите получить: ");
        String labelId = scanner.nextLine();
        System.out.println("Пост: " + postController.getPost(labelId));
    }

    public void updatePost() {
        System.out.println("Введите айди поста, который хотите обновить: ");
        String postId = scanner.nextLine();
        System.out.println("Введите новое сообщение поста: ");
        String newContent = scanner.nextLine();
        System.out.println("Обновленный лейбл: " + postController.updatePost(postId, newContent));
    }

    public void deletePost() {
        System.out.println("Введите айди поста, который хотите удалить: ");
        String postId = scanner.nextLine();
        postController.deletePost(postId);
        System.out.println("Данный пост был удален: " + postController.getPost(postId));
    }
}
