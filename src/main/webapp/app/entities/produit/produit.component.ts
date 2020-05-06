import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from './produit.service';
import { ProduitDeleteDialogComponent } from './produit-delete-dialog.component';

@Component({
  selector: 'jhi-produit',
  templateUrl: './produit.component.html'
})
export class ProduitComponent implements OnInit, OnDestroy {
  produits?: IProduit[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected produitService: ProduitService,
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
      this.produitService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));
      return;
    }

    this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProduits();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProduit): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProduits(): void {
    this.eventSubscriber = this.eventManager.subscribe('produitListModification', () => this.loadAll());
  }

  delete(produit: IProduit): void {
    const modalRef = this.modalService.open(ProduitDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.produit = produit;
  }
}
