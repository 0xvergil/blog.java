package com.ziegler.app.posts;

import java.util.Date;

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
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String mediaUrl;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public Post() {
    }

    public Post(String description, String mediaUrl, User author) {
        this.description = description;
        this.mediaUrl = mediaUrl;
        this.author = author;
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

    public User getAuthor() {
        return author;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}