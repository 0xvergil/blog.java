package com.ziegler.app.notifications;

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
@Table(name = "notifications")
public class Notification {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "recipient_id")
   private User recipient;

   @ManyToOne
   @JoinColumn(name = "post_id")
   private Post post;

   private boolean read = false;

   @Temporal(TemporalType.TIMESTAMP)
   private Date createdAt = new Date();

   public Notification() {
   }

   public Notification(User recipient, Post post) {
      this.recipient = recipient;
      this.post = post;
   }

   public Long getId() {
      return id;
   }

   public User getRecipient() {
      return recipient;
   }

   public Post getPost() {
      return post;
   }

   public boolean isRead() {
      return read;
   }

   public Date getCreatedAt() {
      return createdAt;
   }

   public void setRead(boolean read) {
      this.read = read;
   }
}