import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  usernameOrEmail = '';
  password = '';
  errorMsg = '';
  loading = false;

  constructor(private auth: AuthService, private router: Router) {}

  submit() {
    this.errorMsg = '';
    this.loading = true;

    this.auth.login({
      usernameOrEmail: this.usernameOrEmail,
      password: this.password
    }).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigateByUrl('/dashboard');
      },
      error: (err) => {
        this.loading = false;
        this.errorMsg = err?.error?.message || 'Login incorrect';
      }
    });
  }
}
