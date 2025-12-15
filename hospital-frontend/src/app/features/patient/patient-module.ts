import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { PatientRoutingModule } from './patient-routing-module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    PatientRoutingModule,  // ← This should be RouterModule, not Routes array
    HttpClientModule
  ]
})
export class PatientModule { }
