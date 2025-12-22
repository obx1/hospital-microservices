import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { PatientsComponent } from './pages/patients/patients.component';
import { DoctorsComponent } from './pages/doctors/doctors.component';
import { AppointmentsComponent } from './pages/appointments/appointments.component';
import { BillingComponent } from './pages/billing/billing.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },

  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'patients', component: PatientsComponent, canActivate: [authGuard] },
  { path: 'doctors', component: DoctorsComponent, canActivate: [authGuard] },
  { path: 'appointments', component: AppointmentsComponent, canActivate: [authGuard] },
  { path: 'billing', component: BillingComponent, canActivate: [authGuard] },

  { path: '**', redirectTo: 'dashboard' }
];
