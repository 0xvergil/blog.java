import { Component, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink, Router, NavigationEnd } from '@angular/router';
import { AuthService } from './auth/auth.service';
import { NotificationService } from './notifications/notification';

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

  constructor(
    private authService: AuthService,
    private router: Router,
    private notificationService: NotificationService,
  ) {}

  ngOnInit() {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.username = this.authService.getUsername();
        this.userId = this.authService.getSavedUserId();
        if (this.authService.isLoggedIn()) {
          this.notificationService.getUnreadCount().subscribe({
            next: (count) => (this.unreadCount = count),
          });
        }
      }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
  isAdmin(): boolean {
  const token = this.authService.getToken();
  if (!token) return false;
  const payload = JSON.parse(atob(token.split('.')[1]));
  return payload.role === 'ADMIN';
}
}
