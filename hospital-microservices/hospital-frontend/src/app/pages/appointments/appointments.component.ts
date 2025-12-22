import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppointmentsService } from '../../services/appointments.service';

@Component({
  selector: 'app-appointments',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './appointments.component.html'
})
export class AppointmentsComponent implements OnInit {
  data: any[] = [];
  loading = true;
  error = '';

  constructor(private appointments: AppointmentsService) {}

  ngOnInit() {
    this.appointments.getAll().subscribe({
      next: (res) => { this.data = res; this.loading = false; },
      error: () => { this.error = 'Erreur chargement appointments'; this.loading = false; }
    });
  }
}
