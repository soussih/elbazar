import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICategorie, Categorie } from 'app/shared/model/categorie.model';
import { CategorieService } from './categorie.service';

@Component({
  selector: 'jhi-categorie-update',
  templateUrl: './categorie-update.component.html'
})
export class CategorieUpdateComponent implements OnInit {
  isSaving = false;
  creeLeDp: any;
  modifieLeDp: any;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    description: [],
    position: [null, [Validators.required]],
    etat: [],
    creeLe: [],
    creePar: [],
    modifieLe: [],
    modifiePar: []
  });

  constructor(protected categorieService: CategorieService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorie }) => {
      this.updateForm(categorie);
    });
  }

  updateForm(categorie: ICategorie): void {
    this.editForm.patchValue({
      id: categorie.id,
      nom: categorie.nom,
      description: categorie.description,
      position: categorie.position,
      etat: categorie.etat,
      creeLe: categorie.creeLe,
      creePar: categorie.creePar,
      modifieLe: categorie.modifieLe,
      modifiePar: categorie.modifiePar
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categorie = this.createFromForm();
    if (categorie.id !== undefined) {
      this.subscribeToSaveResponse(this.categorieService.update(categorie));
    } else {
      this.subscribeToSaveResponse(this.categorieService.create(categorie));
    }
  }

  private createFromForm(): ICategorie {
    return {
      ...new Categorie(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value,
      position: this.editForm.get(['position'])!.value,
      etat: this.editForm.get(['etat'])!.value,
      creeLe: this.editForm.get(['creeLe'])!.value,
      creePar: this.editForm.get(['creePar'])!.value,
      modifieLe: this.editForm.get(['modifieLe'])!.value,
      modifiePar: this.editForm.get(['modifiePar'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorie>>): void {
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
