import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./rdv-list/rdv-list').then(m => m.RdvListPage)
  },
  {
    path: 'create',
    loadComponent: () => import('./rdv-create/rdv-create').then(m => m.RdvCreatePage)
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./rdv-edit/rdv-edit').then(m => m.RdvEditPage)
  },
  {
    path: 'cancel/:id',
    loadComponent: () => import('./rdv-cancel/rdv-cancel').then(m => m.RdvCancelPage)
  },
  {
    path: 'delete/:id',
    loadComponent: () => import('./rdv-delete/rdv-delete').then(m => m.RdvDeletePage)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RdvRoutingModule {}
