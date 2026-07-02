package com.ziegler.app.subscriptions;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ziegler.app.user.User;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
   Optional<Subscription> findByFollowerAndFollowing(User follower, User following);

   List<Subscription> findAllByFollower(User follower);

   boolean existsByFollowerAndFollowing(User follower, User following);
}