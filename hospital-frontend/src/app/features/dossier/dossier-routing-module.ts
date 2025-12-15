import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./dossier-list/dossier-list')
        .then(m => m.DossierList)
  },
  {
    path: 'detail/:id',
    loadComponent: () =>
      import('./dossier-detail/dossier-detail')
        .then(m => m.DossierDetail)
  },
  {
    path: 'create',
    loadComponent: () =>
      import('./dossier-create/dossier-create')
        .then(m => m.DossierCreate)
  },
  {
    path: 'delete/:id',
    loadComponent: () =>
      import('./dossier-delete/dossier-delete')
        .then(m => m.DossierDelete)
  },
  {
    path: 'documents/:id',
    loadComponent: () =>
      import('./dossier-documents/dossier-documents')
        .then(m => m.DossierDocuments)
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DossierRoutingModule {}
