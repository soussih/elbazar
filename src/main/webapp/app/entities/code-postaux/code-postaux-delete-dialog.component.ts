import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICodePostaux } from 'app/shared/model/code-postaux.model';
import { CodePostauxService } from './code-postaux.service';

@Component({
  templateUrl: './code-postaux-delete-dialog.component.html'
})
export class CodePostauxDeleteDialogComponent {
  codePostaux?: ICodePostaux;

  constructor(
    protected codePostauxService: CodePostauxService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.codePostauxService.delete(id).subscribe(() => {
      this.eventManager.broadcast('codePostauxListModification');
      this.activeModal.close();
    });
  }
}
