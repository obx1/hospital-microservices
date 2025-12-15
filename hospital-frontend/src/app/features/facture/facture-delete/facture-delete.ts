import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-facture-delete',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './facture-delete.html',
  styleUrls: ['./facture-delete.css']
})
export class FactureDelete implements OnInit {
  id: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
  }

  delete() {
    // Add your delete logic here
    console.log('Deleting facture:', this.id);
    this.router.navigate(['/facture']);
  }
}
