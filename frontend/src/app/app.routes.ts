import { Routes } from '@angular/router';
import { Login } from './auth/login/login';
import { Register } from './auth/register/register';
import { Feed } from './feed/feed';
import { authGuard } from './auth/auth-guard';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'feed', component: Feed, canActivate: [authGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
];
