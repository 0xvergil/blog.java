package com.ziegler.app.posts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ziegler.app.notifications.NotificationService;
import com.ziegler.app.user.User;
import com.ziegler.app.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public PostService(PostRepository postRepository, UserRepository userRepository,
            NotificationService notificationService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public Post createPost(Long userId, String description, String mediaUrl) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post(description, mediaUrl, author);
        Post saved = postRepository.save(post);
        notificationService.notifyFollowers(saved);
        return saved;
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

        // getAuthor() instead of getUser()
        if (!post.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("You cannot delete this post");
        }

        postRepository.delete(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}