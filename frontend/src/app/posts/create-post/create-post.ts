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
  previewUrl: string | null = null;
  isVideo = false;

  constructor(private postService: PostService) {}

  onFileSelected(event: any) {
    this.file = event.target.files[0];
    if (this.file) {
      this.isVideo = this.file.type.startsWith('video/');
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.previewUrl = e.target.result;
      };
      reader.readAsDataURL(this.file);
    }
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
        this.previewUrl = null;
      },
      error: () => {
        this.error = 'Failed to create post';
      },
    });
  }
}
