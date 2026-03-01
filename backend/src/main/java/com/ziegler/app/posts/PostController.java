package com.ziegler.app.posts;

import com.ziegler.app.user.User;
import com.ziegler.app.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    public PostController(PostService postService, UserRepository userRepository) {
        this.postService = postService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(
            Authentication authentication,
            @RequestBody PostRequest request
    ) {
        System.out.println("hell");

        // Get username from JWT
        String username = authentication.getName();

        // Load real User entity from database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create post using real user ID
        Post post = postService.createPost(
                user.getId(),
                request.getDescription(),
                request.getMediaUrl()
        );

        return ResponseEntity.ok(post);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<Post>> getPosts(@PathVariable Long userId) {
        List<Post> posts = postService.getPostsByUser(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/debug")
    public String debug(Authentication authentication) {
        System.out.println("yea B");
        if (authentication == null) {
            return "Authentication is NULL";
        }
        return "User: " + authentication.getName();
    }
    @PostMapping("/test")
    public String testPost() {
        return "POST WORKS";
    }
}