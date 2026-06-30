import { Component, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { AuthService } from './auth/auth.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements OnInit {
  username: string | null = null;

  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.username = this.authService.getUsername();
  }
}
