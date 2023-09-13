package view;

import controller.PostController;
import controller.WriterController;
import model.Post;
import model.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriterView {

    private final Scanner scanner = new Scanner(System.in);
    private final WriterController writerController = new WriterController();
    //TODO: Это нормально или лучше передавать в конструкторе, чтоб не создавать несколько раз тут и в PostView?
    private final PostController postController = new PostController();

    public void createWriter() {
        System.out.println("Введите имя писателя: ");
        String firstName = scanner.nextLine();
        System.out.println("Введите фамилию писателя: ");
        String lastName = scanner.nextLine();
        List<Post> postList = new ArrayList<>();
        while (true) {
            System.out.println("Введите айди поста, который хотите добавить писатлю. Стоп - для остановки");
            String postId = scanner.nextLine();
            if (postId.equalsIgnoreCase("стоп")) {
                break;
            } else {
                Post post = postController.getPost(postId);
                postList.add(post);
            }
            Writer createdWriter = writerController.createWriter(firstName, lastName, postList);
            System.out.println("Созданный писатель: " + createdWriter);
        }
    }

    public void getAllWriters() {
        System.out.println("Все писатели: " + writerController.getAllWriters());
    }

    public void getWriter() {
        System.out.println("Введите айди писателя, которого хотите получить: ");
        String writerId = scanner.nextLine();
        System.out.println("Писатель: " + writerController.getWriter(writerId));
    }

    public void updateWriter() {
        System.out.println("Введите айди писателя, которого хотите обновить: ");
        String writerId = scanner.nextLine();
        System.out.println("Введите новое имя писателя: ");
        String firstName = scanner.nextLine();
        System.out.println("Введите новую фамилию писателя: ");
        String lastName = scanner.nextLine();
        System.out.println("Обновленный писатель: " + writerController.updateWriter(writerId, firstName, lastName));
    }

    public void deleteWriter() {
        System.out.println("Введите айди писателя, которого хотите удалить: ");
        String writerId = scanner.nextLine();
        writerController.deleteWriter(writerId);
        System.out.println("Данный писатель был удален: " + writerController.getWriter(writerId));
    }
}
