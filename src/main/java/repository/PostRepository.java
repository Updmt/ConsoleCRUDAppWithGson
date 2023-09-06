package repository;

import com.google.gson.reflect.TypeToken;
import model.Post;
import model.PostStatus;
import utils.Utils;

import java.util.List;

import static utils.Utils.getCurrentTime;

public class PostRepository extends AbstractFileRepository<Post, Long> {

    private static final String DEFAULT_FILE_NAME = "posts.json";

    public PostRepository() {
        super(DEFAULT_FILE_NAME, new TypeToken<List<Post>>() {}.getType());
    }

    @Override
    public void save(Post post) {
        List<Post> postList = getAll();
        postList.add(post);
        writeListToFile(postList);
    }

    @Override
    public Post getById(Long id) {
        return getAll().stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Post update(Post post) {
        List<Post> postsList = getAll();
        int index = Utils.findIndexInList(postsList, post);
        postsList.set(index, post);
        writeListToFile(postsList);
        return post;
    }

    @Override
    public Post deleteById(Long id) {
        List<Post> writersPost = getAll();
        Post post = getById(id);
        int index = Utils.findIndexInList(writersPost, post);
        post.setPostStatus(PostStatus.DELETED);
        post.setUpdated(getCurrentTime());
        writersPost.set(index, post);
        writeListToFile(writersPost);
        return post;
    }

    public Post findPostByIdFromWriter(List<Post> listPost, String postId) {
        return listPost.stream()
                .filter(p -> p.getId().equals(Long.valueOf(postId)))
                .findFirst()
                .orElse(null);
    }

}
