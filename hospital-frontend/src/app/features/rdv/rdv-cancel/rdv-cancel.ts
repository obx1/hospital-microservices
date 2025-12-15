import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RdvService } from '../rdv.service';

@Component({
  selector: 'app-rdv-cancel',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './rdv-cancel.html',
  styleUrls: ['./rdv-cancel.css']
})
export class RdvCancelPage implements OnInit {

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

    this.rdvService.cancel(this.id).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/rdv']);
      },
      error: () => {
        this.error = 'Erreur lors de l’annulation du rendez-vous';
        this.loading = false;
      }
    });
  }
}
