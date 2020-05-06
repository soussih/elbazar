import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICodePostaux } from 'app/shared/model/code-postaux.model';

@Component({
  selector: 'jhi-code-postaux-detail',
  templateUrl: './code-postaux-detail.component.html'
})
export class CodePostauxDetailComponent implements OnInit {
  codePostaux: ICodePostaux | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ codePostaux }) => (this.codePostaux = codePostaux));
  }

  previousState(): void {
    window.history.back();
  }
}
