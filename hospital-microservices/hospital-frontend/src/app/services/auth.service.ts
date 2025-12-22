import { Injectable, inject, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { isPlatformBrowser } from '@angular/common';
import { Observable, tap } from 'rxjs';
import { API_BASE_URL } from '../core/api';

export interface LoginRequest {
  usernameOrEmail: string;
  password: string;
}

export interface AuthResponse {
  accessToken: string;
  tokenType: string;
  expiresIn: number;
  userId: number;
  username: string;
  email: string;
  role: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private platformId = inject(PLATFORM_ID);

  login(payload: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${API_BASE_URL}/api/auth/login`, payload).pipe(
      tap((res) => {
        this.setToken(res.accessToken);
        this.setUser(res);
      })
    );
  }

  logout(): void {
    this.removeToken();
    this.removeUser();
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getToken(): string | null {
    if (!isPlatformBrowser(this.platformId)) return null;
    return localStorage.getItem('accessToken');
  }

  private setToken(token: string): void {
    if (!isPlatformBrowser(this.platformId)) return;
    localStorage.setItem('accessToken', token);
  }

  private removeToken(): void {
    if (!isPlatformBrowser(this.platformId)) return;
    localStorage.removeItem('accessToken');
  }

  getUser(): Partial<AuthResponse> | null {
    if (!isPlatformBrowser(this.platformId)) return null;
    const raw = localStorage.getItem('user');
    return raw ? JSON.parse(raw) : null;
  }

  private setUser(user: AuthResponse): void {
    if (!isPlatformBrowser(this.platformId)) return;
    localStorage.setItem('user', JSON.stringify({
      userId: user.userId,
      username: user.username,
      email: user.email,
      role: user.role
    }));
  }

  private removeUser(): void {
    if (!isPlatformBrowser(this.platformId)) return;
    localStorage.removeItem('user');
  }
}
