package com.ziegler.app.posts;

import com.ziegler.app.user.UserDTO;
import java.util.Date;

public class PostDTO {
    private Long id;
    private String description;
    private String mediaUrl;
    private Date createdAt;
    private UserDTO author;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.description = post.getDescription();
        this.mediaUrl = post.getMediaUrl();
        this.createdAt = post.getCreatedAt();
        this.author = new UserDTO(post.getAuthor());
    }

    // Getters
    public Long getId() { return id; }
    public String getDescription() { return description; }
    public String getMediaUrl() { return mediaUrl; }
    public Date getCreatedAt() { return createdAt; }
    public UserDTO getAuthor() { return author; }
}