import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-account-create',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './account-create.html',
  styleUrls: ['./account-create.css']
})
export class AccountCreatePage {
  account = {
    username: '',
    email: '',
    password: '',
    role: 'Doctor'
  };

  constructor(private router: Router) {}

  save() {
    console.log('Creating new account:', this.account);
    // Add your save logic here
    // After successful creation, navigate back to list
    this.router.navigate(['/accounts']);
  }
}
