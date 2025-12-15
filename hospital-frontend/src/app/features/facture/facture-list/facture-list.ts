import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-facture-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './facture-list.html',
  styleUrls: ['./facture-list.css']
})
export class FactureListComponent {

  role: 'admin' | 'doctor' | 'patient' = 'admin'; // temporary

  factures = [
    { id: 1, patient: 'John Doe', amount: 220, date: '2025-02-01' },
    { id: 2, patient: 'Sarah Lee', amount: 350, date: '2025-02-05' },
  ];
}
