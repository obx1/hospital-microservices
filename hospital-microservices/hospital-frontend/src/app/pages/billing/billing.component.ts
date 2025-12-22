import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BillingService } from '../../services/billing.service';

@Component({
  selector: 'app-billing',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './billing.component.html'
})
export class BillingComponent implements OnInit {
  data: any[] = [];
  loading = true;
  error = '';

  constructor(private billing: BillingService) {}

  ngOnInit() {
    this.billing.getAll().subscribe({
      next: (res) => { this.data = res; this.loading = false; },
      error: () => { this.error = 'Erreur chargement billing'; this.loading = false; }
    });
  }
}
