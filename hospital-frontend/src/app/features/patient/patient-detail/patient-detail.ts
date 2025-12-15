import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { PatientService, PatientResponse } from '../patient.service';

@Component({
  selector: 'app-patient-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './patient-detail.html',
  styleUrls: ['./patient-detail.css']
})
export class PatientDetailComponent implements OnInit {
  patientId: number | null = null;
  patient: PatientResponse | null = null;
  loading = true;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private patientService: PatientService,
    private cdr: ChangeDetectorRef  // ← Add this
  ) {}

  ngOnInit() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.patientId = parseInt(idParam, 10);
      this.loadPatientData();
    }
  }

  loadPatientData() {
    if (this.patientId === null) return;

    this.loading = true;
    this.patientService.getById(this.patientId).subscribe({
      next: (data) => {
        this.patient = data;
        this.loading = false;
        this.cdr.detectChanges();  // ← Add this
        console.log('Patient loaded:', data);
      },
      error: (err) => {
        console.error('Error loading patient:', err);
        this.error = 'Impossible de charger les données du patient.';
        this.loading = false;
        this.cdr.detectChanges();  // ← Add this
      }
    });
  }

  goBack() {
    this.router.navigate(['/patient']);
  }

  editPatient() {
    console.log('Edit patient:', this.patientId);
  }
}
