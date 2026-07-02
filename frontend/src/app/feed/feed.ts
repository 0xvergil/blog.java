import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { PostService } from '../posts/post';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-feed',
  imports: [FormsModule, RouterLink],
  templateUrl: './feed.html',
  styleUrl: './feed.css',
})
export class Feed implements OnInit {
  posts: any[] = [];
  commentInputs: { [postId: number]: string } = {};

  constructor(
    private postService: PostService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    this.postService.getFeed().subscribe({
      next: (posts) => {
        this.posts = posts;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Failed to load posts', err);
      },
    });
  }

  toggleLike(post: any) {
    this.postService.toggleLike(post.id).subscribe({
      next: (result) => {
        if (result === 'liked') {
          post.likeCount++;
        } else {
          post.likeCount--;
        }
        this.cdr.detectChanges();
      },
    });
  }

  addComment(post: any) {
    const content = this.commentInputs[post.id];
    if (!content?.trim()) return;

    this.postService.addComment(post.id, content).subscribe({
      next: (comment) => {
        post.comments.push(comment);
        this.commentInputs[post.id] = '';
        this.cdr.detectChanges();
      },
    });
  }

  formatDate(dateStr: string): string {
    if (!dateStr) return '';
    return new Date(dateStr).toLocaleString();
  }
}
