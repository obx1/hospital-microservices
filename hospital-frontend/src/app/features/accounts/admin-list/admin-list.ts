import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-admin-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './admin-list.html',
  styleUrls: ['./admin-list.css']
})
export class AdminListPage implements OnInit {

  // Test data for accounts
  accounts: any[] = [];

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.loadAccounts();
  }

  // Load test data
  loadAccounts() {
    // Test data for development
   /* this.accounts = [
      { id: 1, username: 'admin@clinic.com', role: 'Administrator' },
      { id: 2, username: 'dr.smith', role: 'Doctor' },
      { id: 3, username: 'nurse.jones', role: 'Nurse' },
      { id: 4, username: 'receptionist01', role: 'Receptionist' },
      { id: 5, username: 'dr.williams', role: 'Doctor' },
      { id: 6, username: 'admin.support', role: 'Administrator' },
      { id: 7, username: 'nurse.martin', role: 'Nurse' }
    ];*/
  }
}
