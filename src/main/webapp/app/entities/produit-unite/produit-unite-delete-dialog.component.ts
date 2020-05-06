import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProduitUnite } from 'app/shared/model/produit-unite.model';
import { ProduitUniteService } from './produit-unite.service';

@Component({
  templateUrl: './produit-unite-delete-dialog.component.html'
})
export class ProduitUniteDeleteDialogComponent {
  produitUnite?: IProduitUnite;

  constructor(
    protected produitUniteService: ProduitUniteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.produitUniteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('produitUniteListModification');
      this.activeModal.close();
    });
  }
}
