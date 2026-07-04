import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  private baseUrl = 'http://localhost:8080/notifications';

  constructor(
    private http: HttpClient,
    private authService: AuthService,
  ) {}

  private getHeaders() {
    const token = this.authService.getToken();
    return new HttpHeaders({ Authorization: `Bearer ${token}` });
  }

  getNotifications() {
    return this.http.get<any[]>(this.baseUrl, { headers: this.getHeaders() });
  }

  getUnreadCount() {
    return this.http.get<number>(`${this.baseUrl}/unread-count`, { headers: this.getHeaders() });
  }

  markAllRead() {
    return this.http.post(`${this.baseUrl}/mark-read`, {}, { headers: this.getHeaders() });
  }
}
