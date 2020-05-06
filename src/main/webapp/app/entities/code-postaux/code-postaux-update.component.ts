import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICodePostaux, CodePostaux } from 'app/shared/model/code-postaux.model';
import { CodePostauxService } from './code-postaux.service';
import { IZone } from 'app/shared/model/zone.model';
import { ZoneService } from 'app/entities/zone/zone.service';

@Component({
  selector: 'jhi-code-postaux-update',
  templateUrl: './code-postaux-update.component.html'
})
export class CodePostauxUpdateComponent implements OnInit {
  isSaving = false;
  zones: IZone[] = [];
  modifieLeDp: any;

  editForm = this.fb.group({
    id: [],
    gouvernorat: [],
    ville: [],
    localite: [],
    codePostal: [],
    modifieLe: [],
    modifiePar: [],
    zoneId: []
  });

  constructor(
    protected codePostauxService: CodePostauxService,
    protected zoneService: ZoneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ codePostaux }) => {
      this.updateForm(codePostaux);

      this.zoneService.query().subscribe((res: HttpResponse<IZone[]>) => (this.zones = res.body || []));
    });
  }

  updateForm(codePostaux: ICodePostaux): void {
    this.editForm.patchValue({
      id: codePostaux.id,
      gouvernorat: codePostaux.gouvernorat,
      ville: codePostaux.ville,
      localite: codePostaux.localite,
      codePostal: codePostaux.codePostal,
      modifieLe: codePostaux.modifieLe,
      modifiePar: codePostaux.modifiePar,
      zoneId: codePostaux.zoneId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const codePostaux = this.createFromForm();
    if (codePostaux.id !== undefined) {
      this.subscribeToSaveResponse(this.codePostauxService.update(codePostaux));
    } else {
      this.subscribeToSaveResponse(this.codePostauxService.create(codePostaux));
    }
  }

  private createFromForm(): ICodePostaux {
    return {
      ...new CodePostaux(),
      id: this.editForm.get(['id'])!.value,
      gouvernorat: this.editForm.get(['gouvernorat'])!.value,
      ville: this.editForm.get(['ville'])!.value,
      localite: this.editForm.get(['localite'])!.value,
      codePostal: this.editForm.get(['codePostal'])!.value,
      modifieLe: this.editForm.get(['modifieLe'])!.value,
      modifiePar: this.editForm.get(['modifiePar'])!.value,
      zoneId: this.editForm.get(['zoneId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICodePostaux>>): void {
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

  trackById(index: number, item: IZone): any {
    return item.id;
  }
}
