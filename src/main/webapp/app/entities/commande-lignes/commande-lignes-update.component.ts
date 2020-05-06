import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommandeLignes, CommandeLignes } from 'app/shared/model/commande-lignes.model';
import { CommandeLignesService } from './commande-lignes.service';
import { ICommande } from 'app/shared/model/commande.model';
import { CommandeService } from 'app/entities/commande/commande.service';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';

type SelectableEntity = ICommande | IProduit;

@Component({
  selector: 'jhi-commande-lignes-update',
  templateUrl: './commande-lignes-update.component.html'
})
export class CommandeLignesUpdateComponent implements OnInit {
  isSaving = false;
  commandes: ICommande[] = [];
  produits: IProduit[] = [];
  creeLeDp: any;
  modifieLeDp: any;

  editForm = this.fb.group({
    id: [],
    quantite: [],
    prixHT: [],
    tva: [],
    prixTTC: [],
    creeLe: [],
    creePar: [],
    modifieLe: [],
    modifiePar: [],
    commandeId: [],
    idProduitId: []
  });

  constructor(
    protected commandeLignesService: CommandeLignesService,
    protected commandeService: CommandeService,
    protected produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeLignes }) => {
      this.updateForm(commandeLignes);

      this.commandeService.query().subscribe((res: HttpResponse<ICommande[]>) => (this.commandes = res.body || []));

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));
    });
  }

  updateForm(commandeLignes: ICommandeLignes): void {
    this.editForm.patchValue({
      id: commandeLignes.id,
      quantite: commandeLignes.quantite,
      prixHT: commandeLignes.prixHT,
      tva: commandeLignes.tva,
      prixTTC: commandeLignes.prixTTC,
      creeLe: commandeLignes.creeLe,
      creePar: commandeLignes.creePar,
      modifieLe: commandeLignes.modifieLe,
      modifiePar: commandeLignes.modifiePar,
      commandeId: commandeLignes.commandeId,
      idProduitId: commandeLignes.idProduitId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commandeLignes = this.createFromForm();
    if (commandeLignes.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeLignesService.update(commandeLignes));
    } else {
      this.subscribeToSaveResponse(this.commandeLignesService.create(commandeLignes));
    }
  }

  private createFromForm(): ICommandeLignes {
    return {
      ...new CommandeLignes(),
      id: this.editForm.get(['id'])!.value,
      quantite: this.editForm.get(['quantite'])!.value,
      prixHT: this.editForm.get(['prixHT'])!.value,
      tva: this.editForm.get(['tva'])!.value,
      prixTTC: this.editForm.get(['prixTTC'])!.value,
      creeLe: this.editForm.get(['creeLe'])!.value,
      creePar: this.editForm.get(['creePar'])!.value,
      modifieLe: this.editForm.get(['modifieLe'])!.value,
      modifiePar: this.editForm.get(['modifiePar'])!.value,
      commandeId: this.editForm.get(['commandeId'])!.value,
      idProduitId: this.editForm.get(['idProduitId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommandeLignes>>): void {
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
