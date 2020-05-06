import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommandeLignes } from 'app/shared/model/commande-lignes.model';
import { CommandeLignesService } from './commande-lignes.service';
import { CommandeLignesDeleteDialogComponent } from './commande-lignes-delete-dialog.component';

@Component({
  selector: 'jhi-commande-lignes',
  templateUrl: './commande-lignes.component.html'
})
export class CommandeLignesComponent implements OnInit, OnDestroy {
  commandeLignes?: ICommandeLignes[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected commandeLignesService: CommandeLignesService,
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
      this.commandeLignesService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ICommandeLignes[]>) => (this.commandeLignes = res.body || []));
      return;
    }

    this.commandeLignesService.query().subscribe((res: HttpResponse<ICommandeLignes[]>) => (this.commandeLignes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCommandeLignes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommandeLignes): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCommandeLignes(): void {
    this.eventSubscriber = this.eventManager.subscribe('commandeLignesListModification', () => this.loadAll());
  }

  delete(commandeLignes: ICommandeLignes): void {
    const modalRef = this.modalService.open(CommandeLignesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commandeLignes = commandeLignes;
  }
}
