import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-dossier-delete',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dossier-delete.html',
  styleUrls: ['./dossier-delete.css']
})
export class DossierDelete {

  id: string | null = null;

  constructor(private route: ActivatedRoute, private router: Router) {
    this.id = this.route.snapshot.paramMap.get('id');
  }

  confirmDelete() {
    console.log("Deleting dossier:", this.id);

    // Later:
    // this.dossierService.delete(this.id).subscribe(() => {
    //     this.router.navigate(['/dossier']);
    // });

    alert("Dossier supprimé : " + this.id);

    this.router.navigate(['/dossier']);
  }
}
