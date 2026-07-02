package com.ziegler.app.likes;

import org.springframework.stereotype.Service;

import com.ziegler.app.posts.Post;
import com.ziegler.app.posts.PostRepository;
import com.ziegler.app.user.User;
import com.ziegler.app.user.UserRepository;

@Service
public class LikeService {

   private final LikeRepository likeRepository;
   private final PostRepository postRepository;
   private final UserRepository userRepository;

   public LikeService(LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository) {
      this.likeRepository = likeRepository;
      this.postRepository = postRepository;
      this.userRepository = userRepository;
   }

   public String toggleLike(Long postId, String username) {
      Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found"));
      User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

      var existing = likeRepository.findByPostAndUser(post, user);
      if (existing.isPresent()) {
         likeRepository.delete(existing.get());
         return "unliked";
      } else {
         likeRepository.save(new Like(post, user));
         return "liked";
      }
   }

   public long getLikeCount(Long postId) {
      Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found"));
      return likeRepository.countByPost(post);
   }
}