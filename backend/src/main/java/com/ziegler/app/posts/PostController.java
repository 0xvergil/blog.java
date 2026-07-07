package com.ziegler.app.posts;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ziegler.app.comments.CommentDTO;
import com.ziegler.app.comments.CommentService;
import com.ziegler.app.likes.LikeService;
import com.ziegler.app.subscriptions.SubscriptionService;
import com.ziegler.app.user.User;
import com.ziegler.app.user.UserRepository;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;
    private final LikeService likeService;
    private final CommentService commentService;

    private final SubscriptionService subscriptionService;

    public PostController(PostService postService, UserRepository userRepository, LikeService likeService,
            CommentService commentService, SubscriptionService subscriptionService) {
        this.postService = postService;
        this.userRepository = userRepository;
        this.likeService = likeService;
        this.commentService = commentService;
        this.subscriptionService = subscriptionService;
    }

    private PostDTO toDTO(Post post) {
        long likeCount = likeService.getLikeCount(post.getId());
        List<CommentDTO> comments = commentService.getComments(post.getId())
                .stream()
                .map(CommentDTO::new)
                .toList();
        return new PostDTO(post, likeCount, comments);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDTO> createPost(
            Authentication authentication,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile file) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String mediaUrl = null;
        try {
            if (file != null && !file.isEmpty()) {
                String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path uploadPath = Paths.get("uploads/" + filename);
                Files.createDirectories(uploadPath.getParent());
                Files.write(uploadPath, file.getBytes());
                mediaUrl = "/uploads/" + filename;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save file", e);
        }

        Post post = postService.createPost(user.getId(), description, mediaUrl);
        return ResponseEntity.ok(toDTO(post));
    }

    @GetMapping("/me")
    public ResponseEntity<List<PostDTO>> getMyPosts(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<PostDTO> posts = postService.getPostsByUser(user.getId())
                .stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PostDTO>> getPosts(@PathVariable Long userId) {
        List<PostDTO> posts = postService.getPostsByUser(userId)
                .stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/debug")
    public String debug(Authentication authentication) {
        if (authentication == null) {
            return "Authentication is NULL";
        }
        return "User: " + authentication.getName();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            Authentication authentication) {
        String username = authentication.getName();
        postService.deletePost(postId, username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts()
                .stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostDTO>> getFeed(Authentication authentication) {
        List<PostDTO> posts = subscriptionService.getFeedForUser(authentication.getName())
                .stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(
            @PathVariable Long postId,
            @RequestParam String description,
            Authentication authentication) {
        String username = authentication.getName();
        Post post = postService.updatePost(postId, username, description);
        return ResponseEntity.ok(toDTO(post));
    }
}