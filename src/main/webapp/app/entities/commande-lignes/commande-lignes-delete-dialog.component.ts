import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommandeLignes } from 'app/shared/model/commande-lignes.model';
import { CommandeLignesService } from './commande-lignes.service';

@Component({
  templateUrl: './commande-lignes-delete-dialog.component.html'
})
export class CommandeLignesDeleteDialogComponent {
  commandeLignes?: ICommandeLignes;

  constructor(
    protected commandeLignesService: CommandeLignesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commandeLignesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commandeLignesListModification');
      this.activeModal.close();
    });
  }
}
