package com.ziegler.app.comments;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class CommentController {

   private final CommentService commentService;

   public CommentController(CommentService commentService) {
      this.commentService = commentService;
   }

   @PostMapping("/{postId}/comments")
   public ResponseEntity<Comment> addComment(
         @PathVariable Long postId,
         @RequestBody CommentRequest request,
         Authentication authentication) {
      Comment comment = commentService.addComment(postId, authentication.getName(), request.getContent());
      return ResponseEntity.ok(comment);
   }

   @GetMapping("/{postId}/comments")
   public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) {
      return ResponseEntity.ok(commentService.getComments(postId));
   }

   @DeleteMapping("/comments/{commentId}")
   public ResponseEntity<Void> deleteComment(
         @PathVariable Long commentId,
         Authentication authentication) {
      commentService.deleteComment(commentId, authentication.getName());
      return ResponseEntity.noContent().build();
   }
}