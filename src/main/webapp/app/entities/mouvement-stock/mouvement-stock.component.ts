import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMouvementStock } from 'app/shared/model/mouvement-stock.model';
import { MouvementStockService } from './mouvement-stock.service';
import { MouvementStockDeleteDialogComponent } from './mouvement-stock-delete-dialog.component';

@Component({
  selector: 'jhi-mouvement-stock',
  templateUrl: './mouvement-stock.component.html'
})
export class MouvementStockComponent implements OnInit, OnDestroy {
  mouvementStocks?: IMouvementStock[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected mouvementStockService: MouvementStockService,
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
      this.mouvementStockService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IMouvementStock[]>) => (this.mouvementStocks = res.body || []));
      return;
    }

    this.mouvementStockService.query().subscribe((res: HttpResponse<IMouvementStock[]>) => (this.mouvementStocks = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMouvementStocks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMouvementStock): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMouvementStocks(): void {
    this.eventSubscriber = this.eventManager.subscribe('mouvementStockListModification', () => this.loadAll());
  }

  delete(mouvementStock: IMouvementStock): void {
    const modalRef = this.modalService.open(MouvementStockDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mouvementStock = mouvementStock;
  }
}
