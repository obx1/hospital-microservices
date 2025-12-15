import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PatientService } from '../patient.service';

@Component({
  selector: 'app-patient-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './patient-list.html',
  styleUrls: ['./patient-list.css']
})
export class PatientListComponent implements OnInit {

  patients: any[] = [];
  empty = false;

  constructor(
    private patientService: PatientService,
    private cdr: ChangeDetectorRef  // ← Add this
  ) {}

  ngOnInit(): void {
    this.loadPatients();
  }

  loadPatients() {
    this.patientService.getAll().subscribe({
      next: (data: any) => {
        console.log('Raw data from API:', data);
        this.patients = data.content || [];
        console.log('Extracted patients array:', this.patients);
        this.empty = this.patients.length === 0;

        this.cdr.detectChanges();  // ← Force change detection
      },
      error: (err) => {
        console.error('Error loading patients', err);
        this.empty = true;
        this.cdr.detectChanges();  // ← And here too
      }
    });
  }

}
