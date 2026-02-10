package com.ziegler.app.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTestController {

    @GetMapping("/api/ping")
    public String apiPing() {
        return "you are authenticated";
    }
}
