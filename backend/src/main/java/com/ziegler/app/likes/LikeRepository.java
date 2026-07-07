package com.ziegler.app.likes;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ziegler.app.posts.Post;
import com.ziegler.app.user.User;

public interface LikeRepository extends JpaRepository<Like, Long> {
   Optional<Like> findByPostAndUser(Post post, User user);

   long countByPost(Post post);

   List<Like> findByPost(Post post);
}