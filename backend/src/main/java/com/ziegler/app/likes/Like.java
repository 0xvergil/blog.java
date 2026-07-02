package com.ziegler.app.likes;

import com.ziegler.app.posts.Post;
import com.ziegler.app.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "likes")
public class Like {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "post_id")
   private Post post;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

   public Like() {
   }

   public Like(Post post, User user) {
      this.post = post;
      this.user = user;
   }

   public Long getId() {
      return id;
   }

   public Post getPost() {
      return post;
   }

   public User getUser() {
      return user;
   }
}