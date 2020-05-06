import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISousCategorie, SousCategorie } from 'app/shared/model/sous-categorie.model';
import { SousCategorieService } from './sous-categorie.service';
import { ICategorie } from 'app/shared/model/categorie.model';
import { CategorieService } from 'app/entities/categorie/categorie.service';

@Component({
  selector: 'jhi-sous-categorie-update',
  templateUrl: './sous-categorie-update.component.html'
})
export class SousCategorieUpdateComponent implements OnInit {
  isSaving = false;
  categories: ICategorie[] = [];
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
    modifiePar: [],
    categorieId: []
  });

  constructor(
    protected sousCategorieService: SousCategorieService,
    protected categorieService: CategorieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sousCategorie }) => {
      this.updateForm(sousCategorie);

      this.categorieService.query().subscribe((res: HttpResponse<ICategorie[]>) => (this.categories = res.body || []));
    });
  }

  updateForm(sousCategorie: ISousCategorie): void {
    this.editForm.patchValue({
      id: sousCategorie.id,
      nom: sousCategorie.nom,
      description: sousCategorie.description,
      position: sousCategorie.position,
      etat: sousCategorie.etat,
      creeLe: sousCategorie.creeLe,
      creePar: sousCategorie.creePar,
      modifieLe: sousCategorie.modifieLe,
      modifiePar: sousCategorie.modifiePar,
      categorieId: sousCategorie.categorieId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sousCategorie = this.createFromForm();
    if (sousCategorie.id !== undefined) {
      this.subscribeToSaveResponse(this.sousCategorieService.update(sousCategorie));
    } else {
      this.subscribeToSaveResponse(this.sousCategorieService.create(sousCategorie));
    }
  }

  private createFromForm(): ISousCategorie {
    return {
      ...new SousCategorie(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value,
      position: this.editForm.get(['position'])!.value,
      etat: this.editForm.get(['etat'])!.value,
      creeLe: this.editForm.get(['creeLe'])!.value,
      creePar: this.editForm.get(['creePar'])!.value,
      modifieLe: this.editForm.get(['modifieLe'])!.value,
      modifiePar: this.editForm.get(['modifiePar'])!.value,
      categorieId: this.editForm.get(['categorieId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISousCategorie>>): void {
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

  trackById(index: number, item: ICategorie): any {
    return item.id;
  }
}
