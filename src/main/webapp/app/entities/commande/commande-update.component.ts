import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommande, Commande } from 'app/shared/model/commande.model';
import { CommandeService } from './commande.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';
import { IAdresse } from 'app/shared/model/adresse.model';
import { AdresseService } from 'app/entities/adresse/adresse.service';

type SelectableEntity = IClient | IAdresse;

@Component({
  selector: 'jhi-commande-update',
  templateUrl: './commande-update.component.html'
})
export class CommandeUpdateComponent implements OnInit {
  isSaving = false;
  clients: IClient[] = [];
  adresses: IAdresse[] = [];
  dateDp: any;
  dateLivraisonDp: any;
  creeLeDp: any;
  modifieLeDp: any;

  editForm = this.fb.group({
    id: [],
    reference: [null, [Validators.pattern('^[a-zA-Z0-9]{0,12}$')]],
    date: [],
    statut: [],
    naturePiece: [],
    totalHT: [],
    totalTVA: [],
    totalRemise: [],
    totTTC: [],
    zone: [],
    dateLivraison: [],
    creeLe: [],
    creePar: [],
    modifieLe: [],
    modifiePar: [],
    idClientId: [],
    idAdresseId: []
  });

  constructor(
    protected commandeService: CommandeService,
    protected clientService: ClientService,
    protected adresseService: AdresseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commande }) => {
      this.updateForm(commande);

      this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body || []));

      this.adresseService.query().subscribe((res: HttpResponse<IAdresse[]>) => (this.adresses = res.body || []));
    });
  }

  updateForm(commande: ICommande): void {
    this.editForm.patchValue({
      id: commande.id,
      reference: commande.reference,
      date: commande.date,
      statut: commande.statut,
      naturePiece: commande.naturePiece,
      totalHT: commande.totalHT,
      totalTVA: commande.totalTVA,
      totalRemise: commande.totalRemise,
      totTTC: commande.totTTC,
      zone: commande.zone,
      dateLivraison: commande.dateLivraison,
      creeLe: commande.creeLe,
      creePar: commande.creePar,
      modifieLe: commande.modifieLe,
      modifiePar: commande.modifiePar,
      idClientId: commande.idClientId,
      idAdresseId: commande.idAdresseId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commande = this.createFromForm();
    if (commande.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeService.update(commande));
    } else {
      this.subscribeToSaveResponse(this.commandeService.create(commande));
    }
  }

  private createFromForm(): ICommande {
    return {
      ...new Commande(),
      id: this.editForm.get(['id'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      date: this.editForm.get(['date'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      naturePiece: this.editForm.get(['naturePiece'])!.value,
      totalHT: this.editForm.get(['totalHT'])!.value,
      totalTVA: this.editForm.get(['totalTVA'])!.value,
      totalRemise: this.editForm.get(['totalRemise'])!.value,
      totTTC: this.editForm.get(['totTTC'])!.value,
      zone: this.editForm.get(['zone'])!.value,
      dateLivraison: this.editForm.get(['dateLivraison'])!.value,
      creeLe: this.editForm.get(['creeLe'])!.value,
      creePar: this.editForm.get(['creePar'])!.value,
      modifieLe: this.editForm.get(['modifieLe'])!.value,
      modifiePar: this.editForm.get(['modifiePar'])!.value,
      idClientId: this.editForm.get(['idClientId'])!.value,
      idAdresseId: this.editForm.get(['idAdresseId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommande>>): void {
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
