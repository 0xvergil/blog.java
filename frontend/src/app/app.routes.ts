import { Routes } from '@angular/router';
import { Login } from './auth/login/login';
import { Register } from './auth/register/register';
import { Feed } from './feed/feed';
import { authGuard } from './auth/auth-guard';
import { CreatePost } from './posts/create-post/create-post';
import { Profile } from './profile/profile';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'feed', component: Feed, canActivate: [authGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'create-post', component: CreatePost, canActivate: [authGuard] },
  { path: 'profile/:userId', component: Profile, canActivate: [authGuard] },
];
