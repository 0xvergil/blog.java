package com.ziegler.app.auth;

import com.ziegler.app.user.User;
import com.ziegler.app.user.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return userService.register(
                request.getUsername(),
                request.getPassword());
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return userService.login(
                request.getUsername(),
                request.getPassword());
    }
}