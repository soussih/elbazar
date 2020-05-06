import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProdEnumeration } from 'app/shared/model/prod-enumeration.model';
import { ProdEnumerationService } from './prod-enumeration.service';

@Component({
  templateUrl: './prod-enumeration-delete-dialog.component.html'
})
export class ProdEnumerationDeleteDialogComponent {
  prodEnumeration?: IProdEnumeration;

  constructor(
    protected prodEnumerationService: ProdEnumerationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prodEnumerationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('prodEnumerationListModification');
      this.activeModal.close();
    });
  }
}
