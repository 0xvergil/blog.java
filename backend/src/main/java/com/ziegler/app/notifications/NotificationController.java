package com.ziegler.app.notifications;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

   private final NotificationService notificationService;

   public NotificationController(NotificationService notificationService) {
      this.notificationService = notificationService;
   }

   @GetMapping
   public ResponseEntity<List<Notification>> getNotifications(Authentication authentication) {
      return ResponseEntity.ok(notificationService.getNotifications(authentication.getName()));
   }

   @GetMapping("/unread-count")
   public ResponseEntity<Long> getUnreadCount(Authentication authentication) {
      return ResponseEntity.ok(notificationService.getUnreadCount(authentication.getName()));
   }

   @PostMapping("/mark-read")
   public ResponseEntity<Void> markAllRead(Authentication authentication) {
      notificationService.markAllRead(authentication.getName());
      return ResponseEntity.noContent().build();
   }
}