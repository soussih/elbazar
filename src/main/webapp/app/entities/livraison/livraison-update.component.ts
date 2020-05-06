import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILivraison, Livraison } from 'app/shared/model/livraison.model';
import { LivraisonService } from './livraison.service';
import { IZone } from 'app/shared/model/zone.model';
import { ZoneService } from 'app/entities/zone/zone.service';

@Component({
  selector: 'jhi-livraison-update',
  templateUrl: './livraison-update.component.html'
})
export class LivraisonUpdateComponent implements OnInit {
  isSaving = false;
  zones: IZone[] = [];
  creeLeDp: any;
  modifieLeDp: any;

  editForm = this.fb.group({
    id: [],
    categorieClient: [],
    intervalValeur: [],
    frais: [],
    creeLe: [],
    creePar: [],
    modifieLe: [],
    modifiePar: [],
    zoneId: []
  });

  constructor(
    protected livraisonService: LivraisonService,
    protected zoneService: ZoneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ livraison }) => {
      this.updateForm(livraison);

      this.zoneService.query().subscribe((res: HttpResponse<IZone[]>) => (this.zones = res.body || []));
    });
  }

  updateForm(livraison: ILivraison): void {
    this.editForm.patchValue({
      id: livraison.id,
      categorieClient: livraison.categorieClient,
      intervalValeur: livraison.intervalValeur,
      frais: livraison.frais,
      creeLe: livraison.creeLe,
      creePar: livraison.creePar,
      modifieLe: livraison.modifieLe,
      modifiePar: livraison.modifiePar,
      zoneId: livraison.zoneId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const livraison = this.createFromForm();
    if (livraison.id !== undefined) {
      this.subscribeToSaveResponse(this.livraisonService.update(livraison));
    } else {
      this.subscribeToSaveResponse(this.livraisonService.create(livraison));
    }
  }

  private createFromForm(): ILivraison {
    return {
      ...new Livraison(),
      id: this.editForm.get(['id'])!.value,
      categorieClient: this.editForm.get(['categorieClient'])!.value,
      intervalValeur: this.editForm.get(['intervalValeur'])!.value,
      frais: this.editForm.get(['frais'])!.value,
      creeLe: this.editForm.get(['creeLe'])!.value,
      creePar: this.editForm.get(['creePar'])!.value,
      modifieLe: this.editForm.get(['modifieLe'])!.value,
      modifiePar: this.editForm.get(['modifiePar'])!.value,
      zoneId: this.editForm.get(['zoneId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILivraison>>): void {
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
