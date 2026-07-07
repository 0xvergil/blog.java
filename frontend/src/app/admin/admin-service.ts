import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private baseUrl = 'http://localhost:8080/admin';

  constructor(
    private http: HttpClient,
    private authService: AuthService,
  ) {}

  private getHeaders() {
    const token = this.authService.getToken();
    return new HttpHeaders({ Authorization: `Bearer ${token}` });
  }

  getAllUsers() {
    return this.http.get<any[]>(`${this.baseUrl}/users`, { headers: this.getHeaders() });
  }

  deleteUser(userId: number) {
    return this.http.delete(`${this.baseUrl}/users/${userId}`, { headers: this.getHeaders() });
  }

  getAllReports() {
    return this.http.get<any[]>(`${this.baseUrl}/reports`, { headers: this.getHeaders() });
  }

  getAllPosts() {
    return this.http.get<any[]>(`http://localhost:8080/posts/all`, { headers: this.getHeaders() });
  }
  deletePost(postId: number) {
    return this.http.delete(`${this.baseUrl}/posts/${postId}`, { headers: this.getHeaders() });
  }
}
