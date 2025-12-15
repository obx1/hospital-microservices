import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-account-edit',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './account-edit.html',
  styleUrls: ['./account-edit.css']
})
export class AccountEditPage implements OnInit {
  accountId: string | null = null;

  // Dynamic fields to display
  fields = ['username', 'email', 'role'];

  // User data
  user: any = {
    username: '',
    email: '',
    role: ''
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.accountId = this.route.snapshot.paramMap.get('id');
    this.loadUserData();
  }

  loadUserData() {
    // Test data - replace with actual API call
    const testUsers: any = {
      '1': { username: 'admin@clinic.com', email: 'admin@clinic.com', role: 'Administrator' },
      '2': { username: 'dr.smith', email: 'smith@clinic.com', role: 'Doctor' },
      '3': { username: 'nurse.jones', email: 'jones@clinic.com', role: 'Nurse' },
      '4': { username: 'receptionist01', email: 'receptionist@clinic.com', role: 'Receptionist' },
      '5': { username: 'dr.williams', email: 'williams@clinic.com', role: 'Doctor' },
      '6': { username: 'admin.support', email: 'support@clinic.com', role: 'Administrator' },
      '7': { username: 'nurse.martin', email: 'martin@clinic.com', role: 'Nurse' }
    };

    if (this.accountId && testUsers[this.accountId]) {
      this.user = { ...testUsers[this.accountId] };
    }
  }

  save() {
    console.log('Saving user:', this.user);
    // Add your save logic here
    this.router.navigate(['/accounts']);
  }
}
