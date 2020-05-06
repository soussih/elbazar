import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClient, Client } from 'app/shared/model/client.model';
import { ClientService } from './client.service';
import { IAdresse } from 'app/shared/model/adresse.model';
import { AdresseService } from 'app/entities/adresse/adresse.service';

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html'
})
export class ClientUpdateComponent implements OnInit {
  isSaving = false;
  adresses: IAdresse[] = [];
  dateDeNaissanceDp: any;
  derniereVisiteDp: any;
  creeLeDp: any;
  modifieLeDp: any;

  editForm = this.fb.group({
    id: [],
    civilite: [null, [Validators.required]],
    nom: [null, [Validators.required]],
    prenom: [null, [Validators.required]],
    dateDeNaissance: [null, [Validators.required]],
    email: [null, [Validators.required]],
    mobile: [null, [Validators.required]],
    reglement: [],
    etat: [],
    derniereVisite: [],
    totalAchat: [],
    categorie: [null, [Validators.required]],
    pointsFidelite: [],
    creeLe: [],
    creePar: [],
    modifieLe: [],
    modifiePar: [],
    clientId: []
  });

  constructor(
    protected clientService: ClientService,
    protected adresseService: AdresseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ client }) => {
      this.updateForm(client);

      this.adresseService.query().subscribe((res: HttpResponse<IAdresse[]>) => (this.adresses = res.body || []));
    });
  }

  updateForm(client: IClient): void {
    this.editForm.patchValue({
      id: client.id,
      civilite: client.civilite,
      nom: client.nom,
      prenom: client.prenom,
      dateDeNaissance: client.dateDeNaissance,
      email: client.email,
      mobile: client.mobile,
      reglement: client.reglement,
      etat: client.etat,
      derniereVisite: client.derniereVisite,
      totalAchat: client.totalAchat,
      categorie: client.categorie,
      pointsFidelite: client.pointsFidelite,
      creeLe: client.creeLe,
      creePar: client.creePar,
      modifieLe: client.modifieLe,
      modifiePar: client.modifiePar,
      clientId: client.clientId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const client = this.createFromForm();
    if (client.id !== undefined) {
      this.subscribeToSaveResponse(this.clientService.update(client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(client));
    }
  }

  private createFromForm(): IClient {
    return {
      ...new Client(),
      id: this.editForm.get(['id'])!.value,
      civilite: this.editForm.get(['civilite'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      dateDeNaissance: this.editForm.get(['dateDeNaissance'])!.value,
      email: this.editForm.get(['email'])!.value,
      mobile: this.editForm.get(['mobile'])!.value,
      reglement: this.editForm.get(['reglement'])!.value,
      etat: this.editForm.get(['etat'])!.value,
      derniereVisite: this.editForm.get(['derniereVisite'])!.value,
      totalAchat: this.editForm.get(['totalAchat'])!.value,
      categorie: this.editForm.get(['categorie'])!.value,
      pointsFidelite: this.editForm.get(['pointsFidelite'])!.value,
      creeLe: this.editForm.get(['creeLe'])!.value,
      creePar: this.editForm.get(['creePar'])!.value,
      modifieLe: this.editForm.get(['modifieLe'])!.value,
      modifiePar: this.editForm.get(['modifiePar'])!.value,
      clientId: this.editForm.get(['clientId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>): void {
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

  trackById(index: number, item: IAdresse): any {
    return item.id;
  }
}
