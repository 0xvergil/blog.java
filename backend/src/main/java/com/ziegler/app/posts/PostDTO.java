package com.ziegler.app.posts;

import java.util.Date;
import java.util.List;

import com.ziegler.app.comments.CommentDTO;
import com.ziegler.app.user.UserDTO;

public class PostDTO {
    private Long id;
    private String description;
    private String mediaUrl;
    private Date createdAt;
    private UserDTO author;
    private long likeCount;
    private List<CommentDTO> comments;

    public PostDTO(Post post, long likeCount, List<CommentDTO> comments) {
        this.id = post.getId();
        this.description = post.getDescription();
        this.mediaUrl = post.getMediaUrl();
        this.createdAt = post.getCreatedAt();
        this.author = new UserDTO(post.getAuthor());
        this.likeCount = likeCount;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }
}