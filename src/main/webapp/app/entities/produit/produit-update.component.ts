import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProduit, Produit } from 'app/shared/model/produit.model';
import { ProduitService } from './produit.service';
import { ISousCategorie } from 'app/shared/model/sous-categorie.model';
import { SousCategorieService } from 'app/entities/sous-categorie/sous-categorie.service';

@Component({
  selector: 'jhi-produit-update',
  templateUrl: './produit-update.component.html'
})
export class ProduitUpdateComponent implements OnInit {
  isSaving = false;
  souscategories: ISousCategorie[] = [];
  datePremptionDp: any;
  creeLeDp: any;
  modifieLeDp: any;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    codeBarre: [],
    description: [],
    etat: [null, [Validators.required]],
    unite: [],
    marque: [],
    nature: [],
    stockMinimum: [],
    quantiteVenteMax: [],
    datePremption: [],
    prixUnitaireHT: [],
    tauxTva: [],
    prixTtc: [],
    sourceProduit: [null, [Validators.required]],
    horsStock: [],
    rating: [null, [Validators.pattern('^[1-5]$')]],
    remise: [],
    creeLe: [],
    creePar: [],
    modifieLe: [],
    modifiePar: [],
    sousCategorieId: []
  });

  constructor(
    protected produitService: ProduitService,
    protected sousCategorieService: SousCategorieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produit }) => {
      this.updateForm(produit);

      this.sousCategorieService.query().subscribe((res: HttpResponse<ISousCategorie[]>) => (this.souscategories = res.body || []));
    });
  }

  updateForm(produit: IProduit): void {
    this.editForm.patchValue({
      id: produit.id,
      nom: produit.nom,
      codeBarre: produit.codeBarre,
      description: produit.description,
      etat: produit.etat,
      unite: produit.unite,
      marque: produit.marque,
      nature: produit.nature,
      stockMinimum: produit.stockMinimum,
      quantiteVenteMax: produit.quantiteVenteMax,
      datePremption: produit.datePremption,
      prixUnitaireHT: produit.prixUnitaireHT,
      tauxTva: produit.tauxTva,
      prixTtc: produit.prixTtc,
      sourceProduit: produit.sourceProduit,
      horsStock: produit.horsStock,
      rating: produit.rating,
      remise: produit.remise,
      creeLe: produit.creeLe,
      creePar: produit.creePar,
      modifieLe: produit.modifieLe,
      modifiePar: produit.modifiePar,
      sousCategorieId: produit.sousCategorieId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const produit = this.createFromForm();
    if (produit.id !== undefined) {
      this.subscribeToSaveResponse(this.produitService.update(produit));
    } else {
      this.subscribeToSaveResponse(this.produitService.create(produit));
    }
  }

  private createFromForm(): IProduit {
    return {
      ...new Produit(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      codeBarre: this.editForm.get(['codeBarre'])!.value,
      description: this.editForm.get(['description'])!.value,
      etat: this.editForm.get(['etat'])!.value,
      unite: this.editForm.get(['unite'])!.value,
      marque: this.editForm.get(['marque'])!.value,
      nature: this.editForm.get(['nature'])!.value,
      stockMinimum: this.editForm.get(['stockMinimum'])!.value,
      quantiteVenteMax: this.editForm.get(['quantiteVenteMax'])!.value,
      datePremption: this.editForm.get(['datePremption'])!.value,
      prixUnitaireHT: this.editForm.get(['prixUnitaireHT'])!.value,
      tauxTva: this.editForm.get(['tauxTva'])!.value,
      prixTtc: this.editForm.get(['prixTtc'])!.value,
      sourceProduit: this.editForm.get(['sourceProduit'])!.value,
      horsStock: this.editForm.get(['horsStock'])!.value,
      rating: this.editForm.get(['rating'])!.value,
      remise: this.editForm.get(['remise'])!.value,
      creeLe: this.editForm.get(['creeLe'])!.value,
      creePar: this.editForm.get(['creePar'])!.value,
      modifieLe: this.editForm.get(['modifieLe'])!.value,
      modifiePar: this.editForm.get(['modifiePar'])!.value,
      sousCategorieId: this.editForm.get(['sousCategorieId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduit>>): void {
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

  trackById(index: number, item: ISousCategorie): any {
    return item.id;
  }
}
