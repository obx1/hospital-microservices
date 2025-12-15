import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { RdvService, RdvRequest } from '../rdv.service';

@Component({
  selector: 'app-rdv-create',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './rdv-create.html',
  styleUrls: ['./rdv-create.css']
})
export class RdvCreatePage implements OnInit {

  // Dropdown data
  doctors: any[] = [];
  patients: any[] = [];

  // Form model
  form: RdvRequest = {
    patientId: 0,
    doctorId: 0,
    date: '',
    time: '',
    status: 'SCHEDULED'
  };

  loading = false;
  error = '';

  // Backend URLs (adjust ports if needed)
  private doctorsUrl = 'http://localhost:8086/api/doctors';
  private patientsUrl = 'http://localhost:8083/api/patients';

  constructor(
    private http: HttpClient,
    private rdvService: RdvService,
    private router: Router,
    private cdr: ChangeDetectorRef  // ← Add this
  ) {}

  ngOnInit(): void {
    // Load doctors
    this.http.get<any>(this.doctorsUrl).subscribe({
      next: res => {
        this.doctors = res?.content ?? res;
        this.cdr.detectChanges();  // ← Force change detection
      },
      error: err => {
        console.error(err);
        this.error = 'Erreur lors du chargement des médecins.';
        this.cdr.detectChanges();  // ← And here too
      }
    });

    // Load patients
    this.http.get<any>(this.patientsUrl).subscribe({
      next: res => {
        this.patients = res?.content ?? res;
        this.cdr.detectChanges();  // ← Force change detection
      },
      error: err => {
        console.error(err);
        this.error = 'Erreur lors du chargement des patients.';
        this.cdr.detectChanges();  // ← And here too
      }
    });
  }

  create(): void {
    // Ensure IDs are numbers (important for <select>)
    this.form.patientId = Number(this.form.patientId);
    this.form.doctorId = Number(this.form.doctorId);

    console.log('Sending RDV:', this.form);

    this.loading = true;
    this.error = '';

    this.rdvService.create(this.form).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/rdv']);
      },
      error: err => {
        console.error(err);
        this.error = 'Erreur lors de la création du rendez-vous.';
        this.loading = false;
      }
    });
  }
}
