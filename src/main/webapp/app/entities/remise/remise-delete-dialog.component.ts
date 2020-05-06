import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRemise } from 'app/shared/model/remise.model';
import { RemiseService } from './remise.service';

@Component({
  templateUrl: './remise-delete-dialog.component.html'
})
export class RemiseDeleteDialogComponent {
  remise?: IRemise;

  constructor(protected remiseService: RemiseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.remiseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('remiseListModification');
      this.activeModal.close();
    });
  }
}
