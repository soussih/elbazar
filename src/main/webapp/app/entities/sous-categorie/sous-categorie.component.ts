import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISousCategorie } from 'app/shared/model/sous-categorie.model';
import { SousCategorieService } from './sous-categorie.service';
import { SousCategorieDeleteDialogComponent } from './sous-categorie-delete-dialog.component';

@Component({
  selector: 'jhi-sous-categorie',
  templateUrl: './sous-categorie.component.html'
})
export class SousCategorieComponent implements OnInit, OnDestroy {
  sousCategories?: ISousCategorie[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected sousCategorieService: SousCategorieService,
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
      this.sousCategorieService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ISousCategorie[]>) => (this.sousCategories = res.body || []));
      return;
    }

    this.sousCategorieService.query().subscribe((res: HttpResponse<ISousCategorie[]>) => (this.sousCategories = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSousCategories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISousCategorie): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSousCategories(): void {
    this.eventSubscriber = this.eventManager.subscribe('sousCategorieListModification', () => this.loadAll());
  }

  delete(sousCategorie: ISousCategorie): void {
    const modalRef = this.modalService.open(SousCategorieDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sousCategorie = sousCategorie;
  }
}
