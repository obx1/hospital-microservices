import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-dossier-create',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './dossier-create.html',
  styleUrls: ['./dossier-create.css']
})
export class DossierCreate {

  dossier = {
    name: ''
  };

  constructor(private router: Router) {}

  save() {
    console.log("Creating dossier:", this.dossier);

    // Later:
    // this.dossierService.create(this.dossier).subscribe(() => {
    //     this.router.navigate(['/dossier']);
    // });

    // Temporary simulation:
    alert('Dossier créé : ' + this.dossier.name);

    this.router.navigate(['/dossier']);
  }
}
