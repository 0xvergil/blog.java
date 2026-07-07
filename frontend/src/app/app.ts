import { RouterOutlet, RouterLink, Router, NavigationEnd } from '@angular/router';
import { AuthService } from './auth/auth.service';
import { NotificationService } from './notifications/notification';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements OnInit {
  username: string | null = null;
  userId: string | null = null;
  unreadCount: number = 0;
  isAdmin: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private notificationService: NotificationService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.username = this.authService.getUsername();
        this.userId = this.authService.getSavedUserId();
        const token = this.authService.getToken();
        if (token) {
          const payload = JSON.parse(atob(token.split('.')[1]));
          this.isAdmin = payload.role === 'ADMIN';
          this.notificationService.getUnreadCount().subscribe({
            next: (count) => (this.unreadCount = count),
          });
        }
      }
    });
  }

  logout() {
    this.authService.logout();
    window.location.replace('/');
  }
}
