import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-account-delete',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './account-delete.html',
  styleUrls: ['./account-delete.css']
})
export class AccountDeletePage implements OnInit {
  accountId: string | null = null;
  username: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.accountId = this.route.snapshot.paramMap.get('id');
    this.loadAccountInfo();
  }

  loadAccountInfo() {
    // Test data - replace with actual API call
    const testUsers: any = {
      '1': { username: 'admin@clinic.com' },
      '2': { username: 'dr.smith' },
      '3': { username: 'nurse.jones' },
      '4': { username: 'receptionist01' },
      '5': { username: 'dr.williams' },
      '6': { username: 'admin.support' },
      '7': { username: 'nurse.martin' }
    };

    if (this.accountId && testUsers[this.accountId]) {
      this.username = testUsers[this.accountId].username;
    }
  }

  confirmDelete() {
    console.log('Deleting account:', this.accountId);
    // Add your delete logic here
    this.router.navigate(['/accounts']);
  }
}
