import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProduitUnite } from 'app/shared/model/produit-unite.model';

@Component({
  selector: 'jhi-produit-unite-detail',
  templateUrl: './produit-unite-detail.component.html'
})
export class ProduitUniteDetailComponent implements OnInit {
  produitUnite: IProduitUnite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produitUnite }) => (this.produitUnite = produitUnite));
  }

  previousState(): void {
    window.history.back();
  }
}
