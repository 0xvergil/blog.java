import { Routes } from '@angular/router';
import { Login } from './auth/login/login';
import { Register } from './auth/register/register';
import { Feed } from './feed/feed';
import { authGuard } from './auth/auth-guard';
import { CreatePost } from './posts/create-post/create-post';
import { Profile } from './profile/profile';
import { NotificationsPage } from './notifications/notifications-page/notifications-page';
import { Admin } from './admin/admin';
import { adminGuard } from './admin/admin-guard';
import { guestGuard } from './auth/guest-guard';

export const routes: Routes = [
  { path: 'login', component: Login, canActivate: [guestGuard] },
  { path: 'register', component: Register, canActivate: [guestGuard] },
  { path: 'feed', component: Feed, canActivate: [authGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'create-post', component: CreatePost, canActivate: [authGuard] },
  { path: 'profile/:userId', component: Profile, canActivate: [authGuard] },
  { path: 'notifications', component: NotificationsPage, canActivate: [authGuard] },
  { path: 'admin', component: Admin, canActivate: [adminGuard] },
  { path: '**', redirectTo: 'feed' },
];
