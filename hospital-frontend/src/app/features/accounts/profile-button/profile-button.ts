import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-profile-button',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './profile-button.html',
  styleUrls: ['./profile-button.css']
})
export class ProfileButtonComponent {
  constructor(private router: Router) {}

  openProfile() {
    this.router.navigate(['/accounts/account-edit']);
  }
}
