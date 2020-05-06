import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMouvementStock, MouvementStock } from 'app/shared/model/mouvement-stock.model';
import { MouvementStockService } from './mouvement-stock.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';
import { ICommande } from 'app/shared/model/commande.model';
import { CommandeService } from 'app/entities/commande/commande.service';

type SelectableEntity = IProduit | ICommande;

@Component({
  selector: 'jhi-mouvement-stock-update',
  templateUrl: './mouvement-stock-update.component.html'
})
export class MouvementStockUpdateComponent implements OnInit {
  isSaving = false;
  produits: IProduit[] = [];
  commandes: ICommande[] = [];
  dateDp: any;
  creeLeDp: any;
  modifieLeDp: any;

  editForm = this.fb.group({
    id: [],
    type: [],
    date: [],
    sens: [],
    quantite: [],
    creeLe: [],
    creePar: [],
    modifieLe: [],
    modifiePar: [],
    idProduitId: [],
    refCommandeId: []
  });

  constructor(
    protected mouvementStockService: MouvementStockService,
    protected produitService: ProduitService,
    protected commandeService: CommandeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mouvementStock }) => {
      this.updateForm(mouvementStock);

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));

      this.commandeService.query().subscribe((res: HttpResponse<ICommande[]>) => (this.commandes = res.body || []));
    });
  }

  updateForm(mouvementStock: IMouvementStock): void {
    this.editForm.patchValue({
      id: mouvementStock.id,
      type: mouvementStock.type,
      date: mouvementStock.date,
      sens: mouvementStock.sens,
      quantite: mouvementStock.quantite,
      creeLe: mouvementStock.creeLe,
      creePar: mouvementStock.creePar,
      modifieLe: mouvementStock.modifieLe,
      modifiePar: mouvementStock.modifiePar,
      idProduitId: mouvementStock.idProduitId,
      refCommandeId: mouvementStock.refCommandeId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mouvementStock = this.createFromForm();
    if (mouvementStock.id !== undefined) {
      this.subscribeToSaveResponse(this.mouvementStockService.update(mouvementStock));
    } else {
      this.subscribeToSaveResponse(this.mouvementStockService.create(mouvementStock));
    }
  }

  private createFromForm(): IMouvementStock {
    return {
      ...new MouvementStock(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      date: this.editForm.get(['date'])!.value,
      sens: this.editForm.get(['sens'])!.value,
      quantite: this.editForm.get(['quantite'])!.value,
      creeLe: this.editForm.get(['creeLe'])!.value,
      creePar: this.editForm.get(['creePar'])!.value,
      modifieLe: this.editForm.get(['modifieLe'])!.value,
      modifiePar: this.editForm.get(['modifiePar'])!.value,
      idProduitId: this.editForm.get(['idProduitId'])!.value,
      refCommandeId: this.editForm.get(['refCommandeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMouvementStock>>): void {
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
