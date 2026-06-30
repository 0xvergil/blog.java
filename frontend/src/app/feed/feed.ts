import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { PostService } from '../posts/post';

@Component({
  selector: 'app-feed',
  imports: [],
  templateUrl: './feed.html',
  styleUrl: './feed.css',
})
export class Feed implements OnInit {
  posts: any[] = [];

  constructor(
    private postService: PostService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    this.postService.getMyPosts().subscribe({
      next: (posts) => {
        this.posts = posts;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Failed to load posts', err);
      },
    });
  }

  formatDate(dateStr: string): string {
    if (!dateStr) return '';
    return new Date(dateStr).toLocaleString();
  }
}
