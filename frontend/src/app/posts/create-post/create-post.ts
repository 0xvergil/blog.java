import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { PostService } from '../post';

@Component({
  selector: 'app-create-post',
  imports: [FormsModule, RouterLink],
  templateUrl: './create-post.html',
  styleUrl: './create-post.css',
})
export class CreatePost {
  description = '';
  file: File | null = null;
  error = '';
  success = false;

  constructor(private postService: PostService) {}

  onFileSelected(event: any) {
    this.file = event.target.files[0];
  }

  submit() {
    if (!this.description.trim()) {
      this.error = 'Description is required';
      return;
    }

    this.postService.createPost(this.description, this.file ?? undefined).subscribe({
      next: () => {
        this.success = true;
        this.error = '';
      },
      error: () => {
        this.error = 'Failed to create post';
      },
    });
  }
}
