package com.ziegler.app.user;

public class UserDTO {
    private Long id;
    private String username;
    private String role;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole().name();
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
}