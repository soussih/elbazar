import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IZone } from 'app/shared/model/zone.model';
import { ZoneService } from './zone.service';
import { ZoneDeleteDialogComponent } from './zone-delete-dialog.component';

@Component({
  selector: 'jhi-zone',
  templateUrl: './zone.component.html'
})
export class ZoneComponent implements OnInit, OnDestroy {
  zones?: IZone[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected zoneService: ZoneService,
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
      this.zoneService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IZone[]>) => (this.zones = res.body || []));
      return;
    }

    this.zoneService.query().subscribe((res: HttpResponse<IZone[]>) => (this.zones = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInZones();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IZone): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInZones(): void {
    this.eventSubscriber = this.eventManager.subscribe('zoneListModification', () => this.loadAll());
  }

  delete(zone: IZone): void {
    const modalRef = this.modalService.open(ZoneDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.zone = zone;
  }
}
