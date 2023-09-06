package view;

import java.util.List;
import java.util.Scanner;

public class View {

    public <T> void displayObject(T object) {
        System.out.println(object);
    }

    public String getInput(String info) {
        System.out.println(info);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public <T> void displayAllObjects(List<T> objects) {
        System.out.println(objects);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
