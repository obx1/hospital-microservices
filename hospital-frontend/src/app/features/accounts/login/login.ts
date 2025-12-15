import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginPage {

  credentials = {
    username: '',
    password: ''
  };

  constructor(private router: Router) {}

  login() {
    console.log('Logging in with:', this.credentials);

    /**
     * TODO (later):
     * this.authService.login(this.credentials).subscribe(
     *   success => this.router.navigate(['/dashboard']),
     *   error => this.errorMessage = "Invalid credentials"
     * );
     */
  }
}
