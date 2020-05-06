import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProdEnumeration } from 'app/shared/model/prod-enumeration.model';

@Component({
  selector: 'jhi-prod-enumeration-detail',
  templateUrl: './prod-enumeration-detail.component.html'
})
export class ProdEnumerationDetailComponent implements OnInit {
  prodEnumeration: IProdEnumeration | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prodEnumeration }) => (this.prodEnumeration = prodEnumeration));
  }

  previousState(): void {
    window.history.back();
  }
}
