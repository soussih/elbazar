import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRemise, Remise } from 'app/shared/model/remise.model';
import { RemiseService } from './remise.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';

@Component({
  selector: 'jhi-remise-update',
  templateUrl: './remise-update.component.html'
})
export class RemiseUpdateComponent implements OnInit {
  isSaving = false;
  produits: IProduit[] = [];
  debutPromoDp: any;
  finPromoDp: any;
  creeLeDp: any;
  modifieLeDp: any;

  editForm = this.fb.group({
    id: [],
    categorieClient: [],
    prixUnitaire: [],
    remiseCategorie: [],
    remisePromo: [],
    cumulable: [],
    debutPromo: [],
    finPromo: [],
    creeLe: [],
    creePar: [],
    modifieLe: [],
    modifiePar: [],
    idProduitId: []
  });

  constructor(
    protected remiseService: RemiseService,
    protected produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ remise }) => {
      this.updateForm(remise);

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));
    });
  }

  updateForm(remise: IRemise): void {
    this.editForm.patchValue({
      id: remise.id,
      categorieClient: remise.categorieClient,
      prixUnitaire: remise.prixUnitaire,
      remiseCategorie: remise.remiseCategorie,
      remisePromo: remise.remisePromo,
      cumulable: remise.cumulable,
      debutPromo: remise.debutPromo,
      finPromo: remise.finPromo,
      creeLe: remise.creeLe,
      creePar: remise.creePar,
      modifieLe: remise.modifieLe,
      modifiePar: remise.modifiePar,
      idProduitId: remise.idProduitId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const remise = this.createFromForm();
    if (remise.id !== undefined) {
      this.subscribeToSaveResponse(this.remiseService.update(remise));
    } else {
      this.subscribeToSaveResponse(this.remiseService.create(remise));
    }
  }

  private createFromForm(): IRemise {
    return {
      ...new Remise(),
      id: this.editForm.get(['id'])!.value,
      categorieClient: this.editForm.get(['categorieClient'])!.value,
      prixUnitaire: this.editForm.get(['prixUnitaire'])!.value,
      remiseCategorie: this.editForm.get(['remiseCategorie'])!.value,
      remisePromo: this.editForm.get(['remisePromo'])!.value,
      cumulable: this.editForm.get(['cumulable'])!.value,
      debutPromo: this.editForm.get(['debutPromo'])!.value,
      finPromo: this.editForm.get(['finPromo'])!.value,
      creeLe: this.editForm.get(['creeLe'])!.value,
      creePar: this.editForm.get(['creePar'])!.value,
      modifieLe: this.editForm.get(['modifieLe'])!.value,
      modifiePar: this.editForm.get(['modifiePar'])!.value,
      idProduitId: this.editForm.get(['idProduitId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRemise>>): void {
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

  trackById(index: number, item: IProduit): any {
    return item.id;
  }
}
