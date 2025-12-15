import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RdvService } from '../rdv.service';

@Component({
  selector: 'app-rdv-delete',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './rdv-delete.html',
  styleUrls: ['./rdv-delete.css']
})
export class RdvDeletePage implements OnInit {

  id!: number;
  loading = false;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private rdvService: RdvService
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
  }

  confirm() {
    this.loading = true;
    this.error = '';

    this.rdvService.delete(this.id).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/rdv']);
      },
      error: () => {
        this.error = 'Erreur lors de la suppression du rendez-vous';
        this.loading = false;
      }
    });
  }
}
