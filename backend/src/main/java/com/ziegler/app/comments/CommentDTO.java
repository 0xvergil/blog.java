package com.ziegler.app.comments;

import java.util.Date;

import com.ziegler.app.user.UserDTO;

public class CommentDTO {
   private Long id;
   private String content;
   private Date createdAt;
   private UserDTO user;

   public CommentDTO(Comment comment) {
      this.id = comment.getId();
      this.content = comment.getContent();
      this.createdAt = comment.getCreatedAt();
      this.user = new UserDTO(comment.getUser());
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

   public UserDTO getUser() {
      return user;
   }
}