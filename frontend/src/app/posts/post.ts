import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private baseUrl = 'http://localhost:8080/posts';

  constructor(
    private http: HttpClient,
    private authService: AuthService,
  ) {}

  private getHeaders() {
    const token = this.authService.getToken();
    return new HttpHeaders({ Authorization: `Bearer ${token}` });
  }

  getMyPosts() {
    return this.http.get<any[]>(`${this.baseUrl}/me`, { headers: this.getHeaders() });
  }
  getPostsByUser(userId: number) {
    return this.http.get<any[]>(`${this.baseUrl}/${userId}`, { headers: this.getHeaders() });
  }

  createPost(description: string, file?: File) {
    const formData = new FormData();
    formData.append('description', description);
    if (file) formData.append('file', file);

    const token = this.authService.getToken();
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });

    return this.http.post<any>(`${this.baseUrl}`, formData, { headers });
  }

  deletePost(postId: number) {
    return this.http.delete(`${this.baseUrl}/${postId}`, { headers: this.getHeaders() });
  }
}
