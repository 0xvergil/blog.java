package com.ziegler.app.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ziegler.app.comments.CommentRepository;
import com.ziegler.app.likes.LikeRepository;
import com.ziegler.app.notifications.NotificationRepository;
import com.ziegler.app.posts.PostRepository;
import com.ziegler.app.reports.ReportRepository;
import com.ziegler.app.user.UserDTO;
import com.ziegler.app.user.UserRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/admin")
public class AdminController {

   private final UserRepository userRepository;
   private final PostRepository postRepository;
   private final ReportRepository reportRepository;
   private final LikeRepository likeRepository;
   private final CommentRepository commentRepository;
   private final NotificationRepository notificationRepository;

   public AdminController(
         UserRepository userRepository,
         PostRepository postRepository,
         ReportRepository reportRepository,
         LikeRepository likeRepository,
         CommentRepository commentRepository,
         NotificationRepository notificationRepository) {
      this.userRepository = userRepository;
      this.postRepository = postRepository;
      this.reportRepository = reportRepository;
      this.likeRepository = likeRepository;
      this.commentRepository = commentRepository;
      this.notificationRepository = notificationRepository;
   }

   @GetMapping("/users")
   public ResponseEntity<List<UserDTO>> getAllUsers() {
      return ResponseEntity.ok(userRepository.findAll()
            .stream()
            .map(UserDTO::new)
            .toList());
   }

   @DeleteMapping("/users/{userId}")
   public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
      userRepository.deleteById(userId);
      return ResponseEntity.noContent().build();
   }

   @GetMapping("/reports")
   public ResponseEntity<List<Object>> getAllReports() {
      return ResponseEntity.ok(reportRepository.findAllByOrderByCreatedAtDesc()
            .stream()
            .map(r -> (Object) new java.util.HashMap<String, Object>() {
               {
                  put("id", r.getId());
                  put("reason", r.getReason());
                  put("reporter", r.getReporter().getUsername());
                  put("reported", r.getReported().getUsername());
                  put("createdAt", r.getCreatedAt());
               }
            })
            .toList());
   }

   @DeleteMapping("/posts/{postId}")
   @Transactional
   public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
      postRepository.findById(postId).ifPresent(post -> {
         likeRepository.deleteAll(likeRepository.findByPost(post));
         commentRepository.deleteAll(commentRepository.findAllByPost(post));
         notificationRepository.deleteAll(notificationRepository.findByPost(post));
         postRepository.delete(post);
      });
      return ResponseEntity.noContent().build();
   }
}