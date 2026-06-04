import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-register',
  imports: [FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  username = '';
  password = '';
  confirmPassword = '';
  error = '';
  success = false;

  constructor(private authService: AuthService) {}

  register() {
    if (this.password !== this.confirmPassword) {
      this.error = 'Passwords do not match';
      return;
    }

    if (this.username.trim() === '' || this.password.trim() === '') {
      this.error = 'All fields are required';
      return;
    }

    this.authService.register(this.username, this.password).subscribe({
      next: () => {
        this.success = true;
        this.error = '';
      },
      error: () => {
        this.error = 'Registration failed. Username may already exist.';
      },
    });
  }
}
