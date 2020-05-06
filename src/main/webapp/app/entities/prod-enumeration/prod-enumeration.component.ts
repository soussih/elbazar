import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProdEnumeration } from 'app/shared/model/prod-enumeration.model';
import { ProdEnumerationService } from './prod-enumeration.service';
import { ProdEnumerationDeleteDialogComponent } from './prod-enumeration-delete-dialog.component';

@Component({
  selector: 'jhi-prod-enumeration',
  templateUrl: './prod-enumeration.component.html'
})
export class ProdEnumerationComponent implements OnInit, OnDestroy {
  prodEnumerations?: IProdEnumeration[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected prodEnumerationService: ProdEnumerationService,
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
      this.prodEnumerationService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IProdEnumeration[]>) => (this.prodEnumerations = res.body || []));
      return;
    }

    this.prodEnumerationService.query().subscribe((res: HttpResponse<IProdEnumeration[]>) => (this.prodEnumerations = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProdEnumerations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProdEnumeration): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProdEnumerations(): void {
    this.eventSubscriber = this.eventManager.subscribe('prodEnumerationListModification', () => this.loadAll());
  }

  delete(prodEnumeration: IProdEnumeration): void {
    const modalRef = this.modalService.open(ProdEnumerationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.prodEnumeration = prodEnumeration;
  }
}
