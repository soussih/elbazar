import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProduitUnite } from 'app/shared/model/produit-unite.model';
import { ProduitUniteService } from './produit-unite.service';
import { ProduitUniteDeleteDialogComponent } from './produit-unite-delete-dialog.component';

@Component({
  selector: 'jhi-produit-unite',
  templateUrl: './produit-unite.component.html'
})
export class ProduitUniteComponent implements OnInit, OnDestroy {
  produitUnites?: IProduitUnite[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected produitUniteService: ProduitUniteService,
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
      this.produitUniteService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IProduitUnite[]>) => (this.produitUnites = res.body || []));
      return;
    }

    this.produitUniteService.query().subscribe((res: HttpResponse<IProduitUnite[]>) => (this.produitUnites = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProduitUnites();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProduitUnite): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProduitUnites(): void {
    this.eventSubscriber = this.eventManager.subscribe('produitUniteListModification', () => this.loadAll());
  }

  delete(produitUnite: IProduitUnite): void {
    const modalRef = this.modalService.open(ProduitUniteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.produitUnite = produitUnite;
  }
}
