package com.ziegler.app.notifications;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ziegler.app.posts.Post;
import com.ziegler.app.subscriptions.SubscriptionRepository;
import com.ziegler.app.user.User;
import com.ziegler.app.user.UserRepository;

@Service
public class NotificationService {

   private final NotificationRepository notificationRepository;
   private final SubscriptionRepository subscriptionRepository;
   private final UserRepository userRepository;

   public NotificationService(NotificationRepository notificationRepository,
         SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
      this.notificationRepository = notificationRepository;
      this.subscriptionRepository = subscriptionRepository;
      this.userRepository = userRepository;
   }

   public void notifyFollowers(Post post) {
      User author = post.getAuthor();
      subscriptionRepository.findAllByFollower(author).forEach(sub -> {
         Notification notification = new Notification(sub.getFollower(), post);
         notificationRepository.save(notification);
      });
   }

   public List<Notification> getNotifications(String username) {
      User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
      return notificationRepository.findAllByRecipientOrderByCreatedAtDesc(user);
   }

   public long getUnreadCount(String username) {
      User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
      return notificationRepository.countByRecipientAndReadFalse(user);
   }

   public void markAllRead(String username) {
      User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
      List<Notification> notifications = notificationRepository.findAllByRecipientOrderByCreatedAtDesc(user);
      notifications.forEach(n -> n.setRead(true));
      notificationRepository.saveAll(notifications);
   }
}