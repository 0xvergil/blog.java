package com.ziegler.app.likes;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class LikeController {

   private final LikeService likeService;

   public LikeController(LikeService likeService) {
      this.likeService = likeService;
   }

   @PostMapping("/{postId}/like")
   public ResponseEntity<String> toggleLike(
         @PathVariable Long postId,
         Authentication authentication) {
      String result = likeService.toggleLike(postId, authentication.getName());
      return ResponseEntity.ok(result);
   }
}