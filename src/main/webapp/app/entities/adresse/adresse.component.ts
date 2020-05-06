import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdresse } from 'app/shared/model/adresse.model';
import { AdresseService } from './adresse.service';
import { AdresseDeleteDialogComponent } from './adresse-delete-dialog.component';

@Component({
  selector: 'jhi-adresse',
  templateUrl: './adresse.component.html'
})
export class AdresseComponent implements OnInit, OnDestroy {
  adresses?: IAdresse[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected adresseService: AdresseService,
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
      this.adresseService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IAdresse[]>) => (this.adresses = res.body || []));
      return;
    }

    this.adresseService.query().subscribe((res: HttpResponse<IAdresse[]>) => (this.adresses = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAdresses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAdresse): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAdresses(): void {
    this.eventSubscriber = this.eventManager.subscribe('adresseListModification', () => this.loadAll());
  }

  delete(adresse: IAdresse): void {
    const modalRef = this.modalService.open(AdresseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.adresse = adresse;
  }
}
