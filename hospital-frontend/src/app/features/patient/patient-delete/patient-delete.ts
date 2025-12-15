import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { PatientService, PatientResponse } from '../patient.service';

@Component({
  selector: 'app-patient-delete',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './patient-delete.html',
  styleUrls: ['./patient-delete.css']
})
export class PatientDeleteComponent implements OnInit {

  patientId: number | null = null;
  patient: PatientResponse | null = null;
  loading = true;
  deleting = false;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private patientService: PatientService,
    private cdr: ChangeDetectorRef  // ← Add this
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.patientId = parseInt(idParam, 10);
      this.loadPatient();
    }
  }

  loadPatient() {
    if (this.patientId === null) return;

    this.loading = true;
    this.patientService.getById(this.patientId).subscribe({
      next: (data) => {
        this.patient = data;
        this.loading = false;
        this.cdr.detectChanges();  // ← Add this
      },
      error: (err) => {
        console.error('Error loading patient:', err);
        this.error = 'Patient introuvable.';
        this.loading = false;
        this.cdr.detectChanges();  // ← Add this
      }
    });
  }

  confirmDelete() {
    if (this.patientId === null) return;

    this.deleting = true;
    this.error = '';

    this.patientService.delete(this.patientId).subscribe({
      next: () => {
        console.log('Patient deleted successfully');
        this.deleting = false;
        this.cdr.detectChanges();  // ← Add this
        this.router.navigate(['/patient']);
      },
      error: (err) => {
        console.error('Error deleting patient:', err);
        this.error = 'Erreur lors de la suppression. Veuillez réessayer.';
        this.deleting = false;
        this.cdr.detectChanges();  // ← Add this
      }
    });
  }
}
