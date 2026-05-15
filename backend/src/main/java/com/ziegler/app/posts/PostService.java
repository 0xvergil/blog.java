package com.ziegler.app.posts;

import com.ziegler.app.user.User;
import com.ziegler.app.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(Long userId, String description, String mediaUrl) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post(description, mediaUrl, author);
        return postRepository.save(post);
    }

    public List<Post> getPostsByUser(Long userId) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return postRepository.findAllByAuthor(author);
    }

    @Transactional
    public void deletePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Use getAuthor() instead of getUser()
        if (!post.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("You cannot delete this post");
        }

        postRepository.delete(post);
    }
}