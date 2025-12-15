import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';

@Component({
  selector: 'app-dossier-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dossier-detail.html',
  styleUrls: ['./dossier-detail.css']
})
export class DossierDetail {

  id: string | null = null;

  // Le dossier (remplacé plus tard par service backend)
  dossier: any = {
    id: '',
    name: '',
    createdAt: '',
    status: '',
    documents: []
  };

  constructor(private route: ActivatedRoute) {
    this.id = this.route.snapshot.paramMap.get('id');
    this.loadDossier();
  }

  loadDossier() {
    // ⚠ Mock - en attendant service HTTP
    this.dossier = {
      id: this.id,
      name: "Dossier Exemple",
      createdAt: "2024-12-01",
      status: "Actif",
      documents: ["rapport.pdf", "scan.png"]
    };
  }
}
