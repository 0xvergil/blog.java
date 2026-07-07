package com.ziegler.app.notifications;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ziegler.app.posts.Post;
import com.ziegler.app.user.User;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
   List<Notification> findAllByRecipientOrderByCreatedAtDesc(User recipient);

   long countByRecipientAndReadFalse(User recipient);

   List<Notification> findByPost(Post post);
}