package view;

import java.util.Scanner;

public class MainView {
    private final LabelView labelView = new LabelView();
    private final PostView postView = new PostView();
    private final WriterView writerView = new WriterView();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("Введите действие (выйти, " +
                    "создать писателя, получить писателя, получить всех писателей, обновить писателя, удалить писателя, " +
                    "создать пост, получить все посты, получить пост, обновить пост, удалить пост," +
                    "создать лейбл, получить все лейблы, получить лейбл, обновить лейбл, удалить лейбл): ");

            String action = scanner.nextLine().toLowerCase();

            switch (action) {
                case "выйти" -> {
                    return;
                }
                case "создать писателя" -> writerView.createWriter();
                case "получить писателя" -> writerView.getWriter();
                case "получить всех писателей" -> writerView.getAllWriters();
                case "обновить писателя" -> writerView.updateWriter();
                case "удалить писателя" -> writerView.deleteWriter();
                case "создать пост" -> postView.createPost();
                case "получить все посты" -> postView.getAllPosts();
                case "получить пост" -> postView.getPost();
                case "обновить пост" -> postView.updatePost();
                case "удалить пост" -> postView.deletePost();
                case "создать лейбл" -> labelView.createLabel();
                case "получить все лейблы" -> labelView.getAllLabels();
                case "получить лейбл" -> labelView.getLabel();
                case "обновить лейбл" -> labelView.updateLabel();
                case "удалить лейбл" -> labelView.deleteLabel();
                default -> System.out.println("Неизвестная команда. Попробуйте еще раз.");
            }
        }
    }
}
