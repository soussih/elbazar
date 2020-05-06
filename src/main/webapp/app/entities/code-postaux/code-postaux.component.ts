import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICodePostaux } from 'app/shared/model/code-postaux.model';
import { CodePostauxService } from './code-postaux.service';
import { CodePostauxDeleteDialogComponent } from './code-postaux-delete-dialog.component';

@Component({
  selector: 'jhi-code-postaux',
  templateUrl: './code-postaux.component.html'
})
export class CodePostauxComponent implements OnInit, OnDestroy {
  codePostauxes?: ICodePostaux[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected codePostauxService: CodePostauxService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.codePostauxService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ICodePostaux[]>) => (this.codePostauxes = res.body || []));
      return;
    }

    this.codePostauxService.query().subscribe((res: HttpResponse<ICodePostaux[]>) => (this.codePostauxes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCodePostauxes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICodePostaux): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCodePostauxes(): void {
    this.eventSubscriber = this.eventManager.subscribe('codePostauxListModification', () => this.loadAll());
  }

  delete(codePostaux: ICodePostaux): void {
    const modalRef = this.modalService.open(CodePostauxDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.codePostaux = codePostaux;
  }
}
