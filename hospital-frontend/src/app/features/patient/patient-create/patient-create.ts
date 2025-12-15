import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { PatientService, PatientRequest } from '../patient.service';

@Component({
  selector: 'app-patient-create',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './patient-create.html',
  styleUrls: ['./patient-create.css']
})
export class PatientCreateComponent {

  patient = {
    firstName: '',
    lastName: '',
    gender: '' as any,
    birthDate: '',
    email: '',
    phone: '',
    address: ''
  };

  loading = false;
  error = '';

  constructor(
    private router: Router,
    private patientService: PatientService
  ) {}

 save() {
   console.log('Form data being sent:', JSON.stringify(this.patient, null, 2));  // ← Better formatting

   this.loading = true;
   this.error = '';

   this.patientService.create(this.patient).subscribe({
     next: (response) => {
       console.log('Patient created:', response);
       this.loading = false;
       this.router.navigate(['/patient']);
     },
     error: (err) => {
       console.error('Full error:', err);
       console.error('Backend response:', err.error);
       console.error('Error message:', err.message);  // ← Add this
       this.error = 'Erreur lors de la création du patient.';
       this.loading = false;
     }
   });
 }
}
