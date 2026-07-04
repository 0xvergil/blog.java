package com.ziegler.app.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ziegler.app.posts.PostRepository;
import com.ziegler.app.reports.Report;
import com.ziegler.app.reports.ReportRepository;
import com.ziegler.app.user.UserDTO;
import com.ziegler.app.user.UserRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {

   private final UserRepository userRepository;
   private final PostRepository postRepository;
   private final ReportRepository reportRepository;

   public AdminController(UserRepository userRepository, PostRepository postRepository,
         ReportRepository reportRepository) {
      this.userRepository = userRepository;
      this.postRepository = postRepository;
      this.reportRepository = reportRepository;
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
   public ResponseEntity<List<Report>> getAllReports() {
      return ResponseEntity.ok(reportRepository.findAllByOrderByCreatedAtDesc());
   }

   @DeleteMapping("/posts/{postId}")
   public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
      postRepository.deleteById(postId);
      return ResponseEntity.noContent().build();
   }
}