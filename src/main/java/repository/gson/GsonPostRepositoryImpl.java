package repository.gson;

import com.google.gson.reflect.TypeToken;
import model.Post;
import model.PostStatus;
import repository.AbstractFileRepository;
import repository.PostRepository;

import java.util.List;

import static utils.Utils.getCurrentTime;

public class GsonPostRepositoryImpl extends AbstractFileRepository<Post> implements PostRepository {

    private static final String DEFAULT_FILE_NAME = "src/main/resources/posts.json";
    IdRepository postIdRepository = new IdRepository("src/main/resources/posts_id.txt");

    public GsonPostRepositoryImpl() {
        super(DEFAULT_FILE_NAME, new TypeToken<List<Post>>() {
        }.getType());
    }

    @Override
    public Post save(Post post) {
        List<Post> postList = getAll();
        post.setId(postIdRepository.createId());
        postList.add(post);
        writeListToFile(postList);
        return post;
    }

    @Override
    public Post getById(Long id) {
        return getAll().stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Post> getAll() {
        return getAllItemsInternal();
    }

    @Override
    public Post update(Post postToUpdate) {
        List<Post> currentPostList = getAll()
                .stream()
                .map(existingPost -> {
                    if (existingPost.getId().equals(postToUpdate.getId())) {
                        return postToUpdate;
                    } else {
                        return existingPost;
                    }
                }).toList();
        writeListToFile(currentPostList);
        return postToUpdate;
    }

    @Override
    public void deleteById(Long postIdToDelete) {
        List<Post> writersPost = getAll()
                .stream()
                .map(existingPost -> {
                    if (existingPost.getId().equals(postIdToDelete)) {
                        existingPost.setPostStatus(PostStatus.DELETED);
                        existingPost.setUpdated(getCurrentTime());
                    } return existingPost;
                }).toList();
        writeListToFile(writersPost);
    }

    public Post findPostByIdFromWriter(List<Post> listPost, String postId) {
        return listPost.stream()
                .filter(p -> p.getId().equals(Long.valueOf(postId)))
                .findFirst()
                .orElse(null);
    }

}
