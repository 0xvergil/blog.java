package com.ziegler.app.subscriptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ziegler.app.posts.Post;
import com.ziegler.app.posts.PostRepository;
import com.ziegler.app.user.User;
import com.ziegler.app.user.UserRepository;

@Service
public class SubscriptionService {

   private final SubscriptionRepository subscriptionRepository;
   private final UserRepository userRepository;
   private final PostRepository postRepository;

   public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository,
         PostRepository postRepository) {
      this.subscriptionRepository = subscriptionRepository;
      this.userRepository = userRepository;
      this.postRepository = postRepository;
   }

   public String toggleSubscription(String followerUsername, Long followingId) {
      User follower = userRepository.findByUsername(followerUsername)
            .orElseThrow(() -> new RuntimeException("User not found"));
      User following = userRepository.findById(followingId)
            .orElseThrow(() -> new RuntimeException("User not found"));

      if (follower.getId().equals(followingId)) {
         throw new RuntimeException("Cannot subscribe to yourself");
      }

      var existing = subscriptionRepository.findByFollowerAndFollowing(follower, following);
      if (existing.isPresent()) {
         subscriptionRepository.delete(existing.get());
         return "unsubscribed";
      } else {
         subscriptionRepository.save(new Subscription(follower, following));
         return "subscribed";
      }
   }

   public boolean isSubscribed(String followerUsername, Long followingId) {
      User follower = userRepository.findByUsername(followerUsername)
            .orElseThrow(() -> new RuntimeException("User not found"));
      User following = userRepository.findById(followingId)
            .orElseThrow(() -> new RuntimeException("User not found"));
      return subscriptionRepository.existsByFollowerAndFollowing(follower, following);
   }

   public List<Post> getFeedForUser(String username) {
      User follower = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
      List<Subscription> subscriptions = subscriptionRepository.findAllByFollower(follower);
      List<User> following = subscriptions.stream()
            .map(Subscription::getFollowing)
            .collect(Collectors.toList());
      return postRepository.findAllByAuthorIn(following);
   }
}