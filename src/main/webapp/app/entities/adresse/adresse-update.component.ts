import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAdresse, Adresse } from 'app/shared/model/adresse.model';
import { AdresseService } from './adresse.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';
import { IZone } from 'app/shared/model/zone.model';
import { ZoneService } from 'app/entities/zone/zone.service';
import { ICodePostaux } from 'app/shared/model/code-postaux.model';
import { CodePostauxService } from 'app/entities/code-postaux/code-postaux.service';

type SelectableEntity = IClient | IZone | ICodePostaux;

@Component({
  selector: 'jhi-adresse-update',
  templateUrl: './adresse-update.component.html'
})
export class AdresseUpdateComponent implements OnInit {
  isSaving = false;
  clients: IClient[] = [];
  zones: IZone[] = [];
  codepostauxes: ICodePostaux[] = [];
  creeLeDp: any;
  modifieLeDp: any;

  editForm = this.fb.group({
    id: [],
    principale: [],
    nom: [null, [Validators.required]],
    prenom: [null, [Validators.required]],
    adresseLigne1: [null, [Validators.required]],
    adresseLigne2: [],
    gouvernorat: [null, [Validators.required]],
    ville: [],
    cite: [],
    indication: [],
    telephone: [],
    mobile: [],
    creeLe: [],
    creePar: [],
    modifieLe: [],
    modifiePar: [],
    clientId: [],
    zoneId: [],
    codePostalId: []
  });

  constructor(
    protected adresseService: AdresseService,
    protected clientService: ClientService,
    protected zoneService: ZoneService,
    protected codePostauxService: CodePostauxService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adresse }) => {
      this.updateForm(adresse);

      this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body || []));

      this.zoneService.query().subscribe((res: HttpResponse<IZone[]>) => (this.zones = res.body || []));

      this.codePostauxService.query().subscribe((res: HttpResponse<ICodePostaux[]>) => (this.codepostauxes = res.body || []));
    });
  }

  updateForm(adresse: IAdresse): void {
    this.editForm.patchValue({
      id: adresse.id,
      principale: adresse.principale,
      nom: adresse.nom,
      prenom: adresse.prenom,
      adresseLigne1: adresse.adresseLigne1,
      adresseLigne2: adresse.adresseLigne2,
      gouvernorat: adresse.gouvernorat,
      ville: adresse.ville,
      cite: adresse.cite,
      indication: adresse.indication,
      telephone: adresse.telephone,
      mobile: adresse.mobile,
      creeLe: adresse.creeLe,
      creePar: adresse.creePar,
      modifieLe: adresse.modifieLe,
      modifiePar: adresse.modifiePar,
      clientId: adresse.clientId,
      zoneId: adresse.zoneId,
      codePostalId: adresse.codePostalId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adresse = this.createFromForm();
    if (adresse.id !== undefined) {
      this.subscribeToSaveResponse(this.adresseService.update(adresse));
    } else {
      this.subscribeToSaveResponse(this.adresseService.create(adresse));
    }
  }

  private createFromForm(): IAdresse {
    return {
      ...new Adresse(),
      id: this.editForm.get(['id'])!.value,
      principale: this.editForm.get(['principale'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      adresseLigne1: this.editForm.get(['adresseLigne1'])!.value,
      adresseLigne2: this.editForm.get(['adresseLigne2'])!.value,
      gouvernorat: this.editForm.get(['gouvernorat'])!.value,
      ville: this.editForm.get(['ville'])!.value,
      cite: this.editForm.get(['cite'])!.value,
      indication: this.editForm.get(['indication'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      mobile: this.editForm.get(['mobile'])!.value,
      creeLe: this.editForm.get(['creeLe'])!.value,
      creePar: this.editForm.get(['creePar'])!.value,
      modifieLe: this.editForm.get(['modifieLe'])!.value,
      modifiePar: this.editForm.get(['modifiePar'])!.value,
      clientId: this.editForm.get(['clientId'])!.value,
      zoneId: this.editForm.get(['zoneId'])!.value,
      codePostalId: this.editForm.get(['codePostalId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdresse>>): void {
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
