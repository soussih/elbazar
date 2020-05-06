import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILivraison } from 'app/shared/model/livraison.model';
import { LivraisonService } from './livraison.service';

@Component({
  templateUrl: './livraison-delete-dialog.component.html'
})
export class LivraisonDeleteDialogComponent {
  livraison?: ILivraison;

  constructor(protected livraisonService: LivraisonService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.livraisonService.delete(id).subscribe(() => {
      this.eventManager.broadcast('livraisonListModification');
      this.activeModal.close();
    });
  }
}
