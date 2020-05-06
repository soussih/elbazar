import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStock, Stock } from 'app/shared/model/stock.model';
import { StockService } from './stock.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';
import { ICategorie } from 'app/shared/model/categorie.model';
import { CategorieService } from 'app/entities/categorie/categorie.service';
import { ISousCategorie } from 'app/shared/model/sous-categorie.model';
import { SousCategorieService } from 'app/entities/sous-categorie/sous-categorie.service';

type SelectableEntity = IProduit | ICategorie | ISousCategorie;

@Component({
  selector: 'jhi-stock-update',
  templateUrl: './stock-update.component.html'
})
export class StockUpdateComponent implements OnInit {
  isSaving = false;
  produits: IProduit[] = [];
  categories: ICategorie[] = [];
  souscategories: ISousCategorie[] = [];
  derniereEntreDp: any;
  derniereSortieDp: any;
  creeLeDp: any;
  modifieLeDp: any;

  editForm = this.fb.group({
    id: [],
    stockPhysique: [],
    stockDisponible: [],
    stockReserve: [],
    stockCommande: [],
    derniereEntre: [],
    derniereSortie: [],
    alerteStock: [],
    creeLe: [],
    creePar: [],
    modifieLe: [],
    modifiePar: [],
    idProduitId: [],
    idCategorieId: [],
    idSousCategorieId: []
  });

  constructor(
    protected stockService: StockService,
    protected produitService: ProduitService,
    protected categorieService: CategorieService,
    protected sousCategorieService: SousCategorieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stock }) => {
      this.updateForm(stock);

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));

      this.categorieService.query().subscribe((res: HttpResponse<ICategorie[]>) => (this.categories = res.body || []));

      this.sousCategorieService.query().subscribe((res: HttpResponse<ISousCategorie[]>) => (this.souscategories = res.body || []));
    });
  }

  updateForm(stock: IStock): void {
    this.editForm.patchValue({
      id: stock.id,
      stockPhysique: stock.stockPhysique,
      stockDisponible: stock.stockDisponible,
      stockReserve: stock.stockReserve,
      stockCommande: stock.stockCommande,
      derniereEntre: stock.derniereEntre,
      derniereSortie: stock.derniereSortie,
      alerteStock: stock.alerteStock,
      creeLe: stock.creeLe,
      creePar: stock.creePar,
      modifieLe: stock.modifieLe,
      modifiePar: stock.modifiePar,
      idProduitId: stock.idProduitId,
      idCategorieId: stock.idCategorieId,
      idSousCategorieId: stock.idSousCategorieId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stock = this.createFromForm();
    if (stock.id !== undefined) {
      this.subscribeToSaveResponse(this.stockService.update(stock));
    } else {
      this.subscribeToSaveResponse(this.stockService.create(stock));
    }
  }

  private createFromForm(): IStock {
    return {
      ...new Stock(),
      id: this.editForm.get(['id'])!.value,
      stockPhysique: this.editForm.get(['stockPhysique'])!.value,
      stockDisponible: this.editForm.get(['stockDisponible'])!.value,
      stockReserve: this.editForm.get(['stockReserve'])!.value,
      stockCommande: this.editForm.get(['stockCommande'])!.value,
      derniereEntre: this.editForm.get(['derniereEntre'])!.value,
      derniereSortie: this.editForm.get(['derniereSortie'])!.value,
      alerteStock: this.editForm.get(['alerteStock'])!.value,
      creeLe: this.editForm.get(['creeLe'])!.value,
      creePar: this.editForm.get(['creePar'])!.value,
      modifieLe: this.editForm.get(['modifieLe'])!.value,
      modifiePar: this.editForm.get(['modifiePar'])!.value,
      idProduitId: this.editForm.get(['idProduitId'])!.value,
      idCategorieId: this.editForm.get(['idCategorieId'])!.value,
      idSousCategorieId: this.editForm.get(['idSousCategorieId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStock>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
