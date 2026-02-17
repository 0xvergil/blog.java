package com.ziegler.app.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthTestController {

    @GetMapping("/auth/ping")
    public String authPing() {
        return "auth is open";
    }
}