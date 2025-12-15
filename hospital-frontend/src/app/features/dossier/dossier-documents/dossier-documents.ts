import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-dossier-documents',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dossier-documents.html',
  styleUrls: ['./dossier-documents.css']
})
export class DossierDocuments implements OnInit {
  dossierId: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.dossierId = this.route.snapshot.paramMap.get('id');
  }

  goBack() {
    this.router.navigate(['/dossier']);
  }
}
