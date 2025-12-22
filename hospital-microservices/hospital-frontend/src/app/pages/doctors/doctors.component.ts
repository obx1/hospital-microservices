import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DoctorsService } from '../../services/doctors.service';

@Component({
  selector: 'app-doctors',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './doctors.component.html'
})
export class DoctorsComponent implements OnInit {
  data: any[] = [];
  loading = true;
  error = '';

  constructor(private doctors: DoctorsService) {}

  ngOnInit() {
    this.doctors.getAll().subscribe({
      next: (res) => { this.data = res; this.loading = false; },
      error: () => { this.error = 'Erreur chargement doctors'; this.loading = false; }
    });
  }
}
