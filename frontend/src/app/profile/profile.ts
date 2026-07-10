import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostService } from '../posts/post';
import { AuthService } from '../auth/auth.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-profile',
  imports: [FormsModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit {
  posts: any[] = [];
  userId: number = 0;
  isSubscribed: boolean = false;
  isOwnProfile: boolean = false;
  showReportForm: boolean = false;
  reportReason: string = '';
  reportSuccess: boolean = false;

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

  submitReport() {
    if (!this.reportReason.trim()) return;
    if (!confirm('Are you sure you want to report this user?')) return;
    this.postService.reportUser(this.userId, this.reportReason).subscribe({
      next: () => {
        this.reportSuccess = true;
        this.showReportForm = false;
        this.reportReason = '';
        this.cdr.detectChanges();
      },
    });
  }

  formatDate(dateStr: string): string {
    if (!dateStr) return '';
    return new Date(dateStr).toLocaleString();
  }

  // post editing
  editingPostId: number | null = null;
  editDescription: string = '';

  startEdit(post: any) {
    this.editingPostId = post.id;
    this.editDescription = post.description;
  }

  saveEdit(post: any) {
    this.postService.updatePost(post.id, this.editDescription).subscribe({
      next: (updated) => {
        post.description = updated.description;
        this.editingPostId = null;
        this.cdr.detectChanges();
      },
    });
  }

  cancelEdit() {
    this.editingPostId = null;
  }

  deletePost(postId: number) {
    if (!confirm('Delete this post?')) return;
    this.postService.deletePost(postId).subscribe({
      next: () => {
        this.posts = this.posts.filter((p) => p.id !== postId);
        this.cdr.detectChanges();
      },
    });
  }
}
