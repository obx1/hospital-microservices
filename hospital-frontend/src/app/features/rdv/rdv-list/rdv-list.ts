import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { RdvService, RdvResponse } from '../rdv.service';

@Component({
  selector: 'app-rdv-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './rdv-list.html',
  styleUrls: ['./rdv-list.css']
})
export class RdvListPage implements OnInit {

  rdvs: RdvResponse[] = [];
  empty = false;

  constructor(
    private rdvService: RdvService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadRdvs();
  }

  loadRdvs() {
    this.rdvService.getAll().subscribe({
      next: (data) => {
        console.log("Raw RDV list from backend:", data);

        this.rdvs = data || [];
        this.empty = this.rdvs.length === 0;

        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading RDVs:', err);
        this.empty = true;
        this.cdr.detectChanges();
      }
    });
  }

  goEdit(id: number) {
    this.router.navigate(['/rdv/edit', id]);
  }

  goCancel(id: number) {
    this.router.navigate(['/rdv/cancel', id]);
  }

  goDelete(id: number) {
    this.router.navigate(['/rdv/delete', id]);
  }
}
