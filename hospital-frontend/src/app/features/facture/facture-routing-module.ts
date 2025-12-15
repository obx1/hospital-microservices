import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./facture-list/facture-list')
        .then(m => m.FactureListComponent)
  },
  {
    path: 'create',
    loadComponent: () =>
      import('./facture-create/facture-create')
        .then(m => m.FactureCreateComponent)
  },
  {
    path: 'delete/:id',
    loadComponent: () =>
      import('./facture-delete/facture-delete')
        .then(m => m.FactureDelete)
  },
  {
    path: 'generate/:id',
    loadComponent: () =>
      import('./facture-generate/facture-generate')
        .then(m => m.FactureGenerate)
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FactureRoutingModule {}
