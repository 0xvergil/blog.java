package com.ziegler.app.comments;

import java.util.Date;

import com.ziegler.app.posts.Post;
import com.ziegler.app.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "comments")
public class Comment {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String content;

   @Temporal(TemporalType.TIMESTAMP)
   private Date createdAt = new Date();

   @ManyToOne
   @JoinColumn(name = "post_id")
   private Post post;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

   public Comment() {
   }

   public Comment(String content, Post post, User user) {
      this.content = content;
      this.post = post;
      this.user = user;
   }

   public Long getId() {
      return id;
   }

   public String getContent() {
      return content;
   }

   public Date getCreatedAt() {
      return createdAt;
   }

   public Post getPost() {
      return post;
   }

   public User getUser() {
      return user;
   }
}