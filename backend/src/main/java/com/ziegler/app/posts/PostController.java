package com.ziegler.app.posts;

import com.ziegler.app.user.User;
import com.ziegler.app.user.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDTO> createPost(
            Authentication authentication,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile file
    ) {
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
        return ResponseEntity.ok(new PostDTO(post));
    }

    @GetMapping("/me")
    public ResponseEntity<List<PostDTO>> getMyPosts(Authentication authentication) {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<PostDTO> posts = postService.getPostsByUser(user.getId())
                .stream()
                .map(PostDTO::new)
                .toList();

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PostDTO>> getPosts(@PathVariable Long userId) {
        List<PostDTO> posts = postService.getPostsByUser(userId)
                .stream()
                .map(PostDTO::new)
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
            Authentication authentication
    ) {
        String username = authentication.getName();
        postService.deletePost(postId, username);
        return ResponseEntity.noContent().build();
    }
}