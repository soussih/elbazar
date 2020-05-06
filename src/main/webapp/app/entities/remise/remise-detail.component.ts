import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRemise } from 'app/shared/model/remise.model';

@Component({
  selector: 'jhi-remise-detail',
  templateUrl: './remise-detail.component.html'
})
export class RemiseDetailComponent implements OnInit {
  remise: IRemise | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ remise }) => (this.remise = remise));
  }

  previousState(): void {
    window.history.back();
  }
}
