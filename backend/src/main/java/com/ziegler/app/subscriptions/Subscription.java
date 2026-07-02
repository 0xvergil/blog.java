package com.ziegler.app.subscriptions;

import com.ziegler.app.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscriptions")
public class Subscription {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "follower_id")
   private User follower;

   @ManyToOne
   @JoinColumn(name = "following_id")
   private User following;

   public Subscription() {
   }

   public Subscription(User follower, User following) {
      this.follower = follower;
      this.following = following;
   }

   public Long getId() {
      return id;
   }

   public User getFollower() {
      return follower;
   }

   public User getFollowing() {
      return following;
   }
}