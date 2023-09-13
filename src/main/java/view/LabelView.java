package view;

import controller.LabelController;
import model.Label;

import java.util.Scanner;

public class LabelView {

    private final Scanner scanner = new Scanner(System.in);
    private final LabelController labelController = new LabelController();

    public void createLabel() {
        System.out.println("Введите имя: ");
        String name = scanner.nextLine();
        Label createdLabel = labelController.createLabel(name);
        System.out.println("Созданный лейбл: " + createdLabel);
    }

    public void getAllLabels() {
        System.out.println("Все лейблы: " + labelController.getAllLabels());
    }

    public void getLabel() {
        System.out.println("Введите айди лейбла, который хотите получить: ");
        String labelId = scanner.nextLine();
        System.out.println("Лейбл: " + labelController.getLabel(labelId));
    }

    public void updateLabel() {
        System.out.println("Введите айди лейбла, который хотите обновить: ");
        String labelId = scanner.nextLine();
        System.out.println("Введите новое имя лейбла: ");
        String newName = scanner.nextLine();
        System.out.println("Обновленный лейбл: " + labelController.updateLabel(labelId, newName));
    }

    public void deleteLabel() {
        System.out.println("Введите айди лейбла, который хотите удалить: ");
        String labelId = scanner.nextLine();
        labelController.deleteLabel(labelId);
        System.out.println("Данный лейбл был удален: " + labelController.getLabel(labelId));
    }

}
