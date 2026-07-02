package com.ziegler.app.posts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ziegler.app.user.User;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAuthor(User user);

    List<Post> findAllByAuthorIn(List<User> authors);
}