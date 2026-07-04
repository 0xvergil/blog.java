import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { NotificationService } from '../notification';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-notifications-page',
  imports: [RouterLink],
  templateUrl: './notifications-page.html',
  styleUrl: './notifications-page.css',
})
export class NotificationsPage implements OnInit {
  notifications: any[] = [];

  constructor(
    private notificationService: NotificationService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    this.notificationService.getNotifications().subscribe({
      next: (notifications: any[]) => {
        this.notifications = notifications;
        this.cdr.detectChanges();
      },
    });
    this.notificationService.markAllRead().subscribe();
  }
}
