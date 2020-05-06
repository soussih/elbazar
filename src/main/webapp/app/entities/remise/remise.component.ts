import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRemise } from 'app/shared/model/remise.model';
import { RemiseService } from './remise.service';
import { RemiseDeleteDialogComponent } from './remise-delete-dialog.component';

@Component({
  selector: 'jhi-remise',
  templateUrl: './remise.component.html'
})
export class RemiseComponent implements OnInit, OnDestroy {
  remises?: IRemise[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected remiseService: RemiseService,
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
      this.remiseService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IRemise[]>) => (this.remises = res.body || []));
      return;
    }

    this.remiseService.query().subscribe((res: HttpResponse<IRemise[]>) => (this.remises = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRemises();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRemise): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRemises(): void {
    this.eventSubscriber = this.eventManager.subscribe('remiseListModification', () => this.loadAll());
  }

  delete(remise: IRemise): void {
    const modalRef = this.modalService.open(RemiseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.remise = remise;
  }
}
