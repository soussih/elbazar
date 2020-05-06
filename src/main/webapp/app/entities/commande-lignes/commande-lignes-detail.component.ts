import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommandeLignes } from 'app/shared/model/commande-lignes.model';

@Component({
  selector: 'jhi-commande-lignes-detail',
  templateUrl: './commande-lignes-detail.component.html'
})
export class CommandeLignesDetailComponent implements OnInit {
  commandeLignes: ICommandeLignes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeLignes }) => (this.commandeLignes = commandeLignes));
  }

  previousState(): void {
    window.history.back();
  }
}
