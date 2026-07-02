package com.ziegler.app.comments;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ziegler.app.posts.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {
   List<Comment> findAllByPost(Post post);

   long countByPost(Post post);
}