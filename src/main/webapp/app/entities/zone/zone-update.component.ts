import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IZone, Zone } from 'app/shared/model/zone.model';
import { ZoneService } from './zone.service';

@Component({
  selector: 'jhi-zone-update',
  templateUrl: './zone-update.component.html'
})
export class ZoneUpdateComponent implements OnInit {
  isSaving = false;
  creeLeDp: any;
  modifieLeDp: any;

  editForm = this.fb.group({
    id: [],
    nom: [],
    creeLe: [],
    creePar: [],
    modifieLe: [],
    modifiePar: []
  });

  constructor(protected zoneService: ZoneService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ zone }) => {
      this.updateForm(zone);
    });
  }

  updateForm(zone: IZone): void {
    this.editForm.patchValue({
      id: zone.id,
      nom: zone.nom,
      creeLe: zone.creeLe,
      creePar: zone.creePar,
      modifieLe: zone.modifieLe,
      modifiePar: zone.modifiePar
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const zone = this.createFromForm();
    if (zone.id !== undefined) {
      this.subscribeToSaveResponse(this.zoneService.update(zone));
    } else {
      this.subscribeToSaveResponse(this.zoneService.create(zone));
    }
  }

  private createFromForm(): IZone {
    return {
      ...new Zone(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      creeLe: this.editForm.get(['creeLe'])!.value,
      creePar: this.editForm.get(['creePar'])!.value,
      modifieLe: this.editForm.get(['modifieLe'])!.value,
      modifiePar: this.editForm.get(['modifiePar'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IZone>>): void {
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
