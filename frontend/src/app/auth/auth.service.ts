import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  login(username: string, password: string) {
    return this.http.post(
      `${this.baseUrl}/login`,
      { username, password },
      { responseType: 'text' },
    );
  }
  register(username: string, password: string) {
    return this.http.post(
      `${this.baseUrl}/register`,
      { username, password },
      { responseType: 'text' },
    );
  }

  saveToken(token: string) {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getUsername(): string | null {
    const token = this.getToken();
    if (!token) return null;
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.sub;
  }

  getMe() {
    const headers = new HttpHeaders({ Authorization: `Bearer ${this.getToken()}` });
    return this.http.get<any>('http://localhost:8080/auth/me', { headers });
  }
  saveUserId(id: number) {
    localStorage.setItem('userId', id.toString());
  }

  getSavedUserId(): string | null {
    return localStorage.getItem('userId');
  }
}
