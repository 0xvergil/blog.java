package com.ziegler.app.subscriptions;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

   private final SubscriptionService subscriptionService;

   public SubscriptionController(SubscriptionService subscriptionService) {
      this.subscriptionService = subscriptionService;
   }

   @PostMapping("/{userId}")
   public ResponseEntity<String> toggleSubscription(
         @PathVariable Long userId,
         Authentication authentication) {
      String result = subscriptionService.toggleSubscription(authentication.getName(), userId);
      return ResponseEntity.ok(result);
   }

   @GetMapping("/is-subscribed/{userId}")
   public ResponseEntity<Boolean> isSubscribed(
         @PathVariable Long userId,
         Authentication authentication) {
      boolean result = subscriptionService.isSubscribed(authentication.getName(), userId);
      return ResponseEntity.ok(result);
   }
}