import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { RdvService, RdvRequest } from '../rdv.service';

@Component({
  selector: 'app-rdv-edit',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './rdv-edit.html',
  styleUrls: ['./rdv-edit.css']
})
export class RdvEditPage implements OnInit {

  id!: number;

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

  // Backend URLs
  private doctorsUrl = 'http://localhost:8086/api/doctors';
  private patientsUrl = 'http://localhost:8083/api/patients';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private rdvService: RdvService,
    private cdr: ChangeDetectorRef
  ) {
    this.id = Number(this.route.snapshot.queryParamMap.get('id'));
  }

  ngOnInit(): void {
    this.loadDoctors();
    this.loadPatients();
    this.loadRdv();
  }

  loadDoctors() {
    this.http.get<any>(this.doctorsUrl).subscribe({
      next: res => {
        this.doctors = res?.content ?? res;
        this.cdr.detectChanges();
      },
      error: err => {
        console.error('Error loading doctors:', err);
        this.error = 'Erreur lors du chargement des médecins.';
        this.cdr.detectChanges();
      }
    });
  }

  loadPatients() {
    this.http.get<any>(this.patientsUrl).subscribe({
      next: res => {
        this.patients = res?.content ?? res;
        this.cdr.detectChanges();
      },
      error: err => {
        console.error('Error loading patients:', err);
        this.error = 'Erreur lors du chargement des patients.';
        this.cdr.detectChanges();
      }
    });
  }

  loadRdv() {
    if (this.id) {
      this.rdvService.getById(this.id).subscribe({
        next: (rdv) => {
          this.form = {
            patientId: rdv.patientId,
            doctorId: rdv.doctorId,
            date: rdv.date,
            time: rdv.time,
            status: rdv.status
          };
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.error('Error loading RDV:', err);
          this.error = 'Erreur lors du chargement du rendez-vous.';
          this.cdr.detectChanges();
        }
      });
    }
  }

  save() {
    // Ensure IDs are numbers
    this.form.patientId = Number(this.form.patientId);
    this.form.doctorId = Number(this.form.doctorId);

    console.log('Updating RDV:', this.id, this.form);

    this.loading = true;
    this.error = '';

    this.rdvService.update(this.id, this.form).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/rdv']);
      },
      error: err => {
        console.error('Error updating RDV:', err);
        this.error = 'Erreur lors de la mise à jour du rendez-vous.';
        this.loading = false;
      }
    });
  }
}
