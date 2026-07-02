import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostService } from '../posts/post';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-profile',
  imports: [],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit {
  posts: any[] = [];
  userId: number = 0;
  isSubscribed: boolean = false;
  isOwnProfile: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('userId');
    this.userId = Number(id);
    const myId = this.authService.getSavedUserId();
    this.isOwnProfile = myId === id;

    this.postService.getPostsByUser(this.userId).subscribe({
      next: (posts) => {
        this.posts = posts;
        this.cdr.detectChanges();
      },
    });

    if (!this.isOwnProfile) {
      this.postService.isSubscribed(this.userId).subscribe({
        next: (result) => {
          this.isSubscribed = result;
          this.cdr.detectChanges();
        },
      });
    }
  }

  toggleSubscription() {
    this.postService.toggleSubscription(this.userId).subscribe({
      next: (result) => {
        this.isSubscribed = result === 'subscribed';
        this.cdr.detectChanges();
      },
    });
  }

  formatDate(dateStr: string): string {
    if (!dateStr) return '';
    return new Date(dateStr).toLocaleString();
  }
}
