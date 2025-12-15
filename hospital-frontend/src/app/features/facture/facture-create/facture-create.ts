import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-facture-create',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './facture-create.html',
  styleUrls: ['./facture-create.css']
})
export class FactureCreateComponent {
  facture = {
    patient: '',
    amount: 0,
    date: ''
  };

  constructor(private router: Router) {}

  save() {
    // Add your save logic here
    console.log('Saving facture:', this.facture);
    this.router.navigate(['/facture']);
  }
}
