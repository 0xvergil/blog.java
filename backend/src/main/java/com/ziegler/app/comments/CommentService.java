package com.ziegler.app.comments;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ziegler.app.posts.Post;
import com.ziegler.app.posts.PostRepository;
import com.ziegler.app.user.User;
import com.ziegler.app.user.UserRepository;

@Service
public class CommentService {

   private final CommentRepository commentRepository;
   private final PostRepository postRepository;
   private final UserRepository userRepository;

   public CommentService(CommentRepository commentRepository, PostRepository postRepository,
         UserRepository userRepository) {
      this.commentRepository = commentRepository;
      this.postRepository = postRepository;
      this.userRepository = userRepository;
   }

   public Comment addComment(Long postId, String username, String content) {
      Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found"));
      User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
      return commentRepository.save(new Comment(content, post, user));
   }

   public List<Comment> getComments(Long postId) {
      Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found"));
      return commentRepository.findAllByPost(post);
   }

   public void deleteComment(Long commentId, String username) {
      Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new RuntimeException("Comment not found"));
      if (!comment.getUser().getUsername().equals(username)) {
         throw new RuntimeException("You cannot delete this comment");
      }
      commentRepository.delete(comment);
   }
}