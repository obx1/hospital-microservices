import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./patient-list/patient-list')
        .then(m => m.PatientListComponent)
  },
  {
    path: 'create',
    loadComponent: () =>
      import('./patient-create/patient-create')
        .then(m => m.PatientCreateComponent)
  },
  {
    path: 'delete/:id',
    loadComponent: () =>
      import('./patient-delete/patient-delete')
        .then(m => m.PatientDeleteComponent)
  },
  {
    path: 'detail/:id',
    loadComponent: () =>
      import('./patient-detail/patient-detail')
        .then(m => m.PatientDetailComponent)
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PatientRoutingModule {}
