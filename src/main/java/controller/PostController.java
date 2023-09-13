package controller;

import model.*;
import repository.PostRepository;
import repository.gson.GsonPostRepositoryImpl;

import java.util.List;

import static utils.Utils.getCurrentTime;

public class PostController {
    private final PostRepository postRepository = new GsonPostRepositoryImpl();

    public Post createPost(String content, List<Label> labelList) {
        Post post = Post.builder()
                .content(content)
                .created(getCurrentTime())
                .updated(getCurrentTime())
                .postStatus(PostStatus.ACTIVE)
                .labels(labelList)
                .build();
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.getAll();
    }

    public Post getPost(String postId) {
        return postRepository.getById(Long.valueOf(postId));
    }

    public Post updatePost(String postId, String updatedContent) {
        Post post = postRepository.getById(Long.valueOf(postId));
        post.setContent(updatedContent);
        post.setUpdated(getCurrentTime());
        post.setPostStatus(PostStatus.UNDER_REVIEW);
        return postRepository.update(post);
    }

    public void deletePost(String postId) {
        postRepository.deleteById(Long.valueOf(postId));
    }

}
