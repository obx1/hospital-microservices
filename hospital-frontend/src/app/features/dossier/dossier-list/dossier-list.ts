import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-dossier-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dossier-list.html',
  styleUrls: ['./dossier-list.css']
})
export class DossierList implements OnInit {

  // Empty array ready to be filled later by backend response
  dossiers: any[] = [];

  constructor() {}

  ngOnInit(): void {
    this.loadDossiers();
  }

  // Temporary placeholder before connecting backend
  loadDossiers() {
    /**
     * TODO: Later replace this with a real API call using DossierService.
     * e.g.
     * this.dossierService.getAll().subscribe(data => this.dossiers = data);
     */

    // For now do nothing — leaves dossiers empty instead of using mock data.
    this.dossiers = [];
  }
}
