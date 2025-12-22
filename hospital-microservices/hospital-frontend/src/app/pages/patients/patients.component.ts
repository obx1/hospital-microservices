import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientsService } from '../../services/patients.service';

@Component({
  selector: 'app-patients',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './patients.component.html'
})
export class PatientsComponent implements OnInit {
  data: any[] = [];
  loading = true;
  error = '';

  constructor(private patients: PatientsService) {}

  ngOnInit() {
    this.patients.getAll().subscribe({
      next: (res) => { this.data = res; this.loading = false; },
      error: () => { this.error = 'Erreur chargement patients'; this.loading = false; }
    });
  }
}
