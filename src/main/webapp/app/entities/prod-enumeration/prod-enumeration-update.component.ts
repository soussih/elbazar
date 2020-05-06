import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProdEnumeration, ProdEnumeration } from 'app/shared/model/prod-enumeration.model';
import { ProdEnumerationService } from './prod-enumeration.service';

@Component({
  selector: 'jhi-prod-enumeration-update',
  templateUrl: './prod-enumeration-update.component.html'
})
export class ProdEnumerationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    type: [],
    nom: []
  });

  constructor(
    protected prodEnumerationService: ProdEnumerationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prodEnumeration }) => {
      this.updateForm(prodEnumeration);
    });
  }

  updateForm(prodEnumeration: IProdEnumeration): void {
    this.editForm.patchValue({
      id: prodEnumeration.id,
      type: prodEnumeration.type,
      nom: prodEnumeration.nom
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prodEnumeration = this.createFromForm();
    if (prodEnumeration.id !== undefined) {
      this.subscribeToSaveResponse(this.prodEnumerationService.update(prodEnumeration));
    } else {
      this.subscribeToSaveResponse(this.prodEnumerationService.create(prodEnumeration));
    }
  }

  private createFromForm(): IProdEnumeration {
    return {
      ...new ProdEnumeration(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      nom: this.editForm.get(['nom'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProdEnumeration>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
