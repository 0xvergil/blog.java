package com.ziegler.app.user;
import jakarta.persistence.*;

@Entity
@Table(name= "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String hashedPassword;
    private Role role;
    private boolean enabled;
    public User() {

    }
    public User(String username, String email, String hashedPassword, Role role) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = role;
        this.enabled = true;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    public Role getRole() {
        return role;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public String getPassword() {
        return hashedPassword;
    }
}