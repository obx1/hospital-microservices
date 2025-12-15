import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-facture-generate',
  standalone: true,
  imports: [CommonModule],
  template: `
    <h2>Génération de la Facture</h2>
    <p>Facture ID: {{ id }}</p>
    <p>Redirection vers génération du PDF… (simulation)</p>
  `
})
export class FactureGenerate{

  id: number = 0;

  constructor(private route: ActivatedRoute) {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
  }
}
