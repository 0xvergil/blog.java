import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { AdminService } from './admin-service';

@Component({
  selector: 'app-admin',
  imports: [],
  templateUrl: './admin.html',
  styleUrl: './admin.css',
})
export class Admin implements OnInit {
  users: any[] = [];
  reports: any[] = [];

  constructor(private adminService: AdminService, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    this.adminService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.cdr.detectChanges();
      }
    });

    this.adminService.getAllReports().subscribe({
      next: (reports) => {
        this.reports = reports;
        this.cdr.detectChanges();
      }
    });
  }

  deleteUser(userId: number) {
    if (!confirm('Are you sure you want to delete this user?')) return;
    this.adminService.deleteUser(userId).subscribe({
      next: () => {
        this.users = this.users.filter(u => u.id !== userId);
        this.cdr.detectChanges();
      }
    });
  }
}