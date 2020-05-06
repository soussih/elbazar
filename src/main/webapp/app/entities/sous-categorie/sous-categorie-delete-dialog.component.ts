import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISousCategorie } from 'app/shared/model/sous-categorie.model';
import { SousCategorieService } from './sous-categorie.service';

@Component({
  templateUrl: './sous-categorie-delete-dialog.component.html'
})
export class SousCategorieDeleteDialogComponent {
  sousCategorie?: ISousCategorie;

  constructor(
    protected sousCategorieService: SousCategorieService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sousCategorieService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sousCategorieListModification');
      this.activeModal.close();
    });
  }
}
