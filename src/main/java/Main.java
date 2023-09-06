import controller.LabelController;
import controller.PostController;
import controller.WriterController;
import repository.IdRepository;
import repository.LabelRepository;
import repository.PostRepository;
import repository.WriterRepository;
import view.View;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        WriterRepository writerRepository = new WriterRepository();
        PostRepository postRepository = new PostRepository();
        LabelRepository labelRepository = new LabelRepository();
        IdRepository writerIdRepository = new IdRepository("writers_id.txt");
        IdRepository postIdRepository = new IdRepository("posts_id.txt");
        IdRepository labelIdRepository = new IdRepository("labels_id.txt");
        LabelController labelController = new LabelController(view, writerRepository, labelIdRepository,
                labelRepository, postRepository);
        PostController postController = new PostController(view, postRepository, postIdRepository, writerRepository, labelController);
        WriterController writerController = new WriterController(view, writerRepository, postController, writerIdRepository);

        while (true) {
            String action = view.getInput("Введите действие (выйти, " +
                    "создать писателя, получить писателя, получить всех писателей, обновить писателя, удалить писателя, " +
                    "создать пост, получить все посты, получить пост, обновить пост, удалить пост," +
                    "создать тег, получить все теги, получить тег, обновить тег, удалить тег): ").trim();

            if (action.equalsIgnoreCase("выйти")) {
                break;
            } else if (action.equalsIgnoreCase("создать писателя")) {
                writerController.createWriter();
            } else if (action.equalsIgnoreCase("получить писателя")) {
                writerController.getWriter();
            } else if (action.equalsIgnoreCase("получить всех писателей")) {
                writerController.getAllWriters();
            } else if (action.equalsIgnoreCase("обновить писателя")) {
                writerController.updateWriter();
            } else if (action.equalsIgnoreCase("удалить писателя")) {
                writerController.deleteWriter();
            } else if (action.equalsIgnoreCase("создать пост")) {
                postController.createPost();
            } else if (action.equalsIgnoreCase("получить все посты")) {
                postController.getAllPosts();
            } else if (action.equalsIgnoreCase("получить пост")) {
                postController.getPost();
            } else if (action.equalsIgnoreCase("обновить пост")) {
                postController.updatePost();
            } else if (action.equalsIgnoreCase("удалить пост")) {
                postController.deletePost();
            } else if (action.equalsIgnoreCase("создать тег")) {
                labelController.createLabel();
            } else if (action.equalsIgnoreCase("получить все теги")) {
                labelController.getAllLabels();
            } else if (action.equalsIgnoreCase("получить тег")) {
                labelController.getLabel();
            } else if (action.equalsIgnoreCase("обновить тег")) {
                labelController.updateLabel();
            } else if (action.equalsIgnoreCase("удалить тег")) {
                labelController.deleteLabel();
            }
        }
    }
}