import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProduitUnite, ProduitUnite } from 'app/shared/model/produit-unite.model';
import { ProduitUniteService } from './produit-unite.service';

@Component({
  selector: 'jhi-produit-unite-update',
  templateUrl: './produit-unite-update.component.html'
})
export class ProduitUniteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    prdunite: []
  });

  constructor(protected produitUniteService: ProduitUniteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produitUnite }) => {
      this.updateForm(produitUnite);
    });
  }

  updateForm(produitUnite: IProduitUnite): void {
    this.editForm.patchValue({
      id: produitUnite.id,
      prdunite: produitUnite.prdunite
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const produitUnite = this.createFromForm();
    if (produitUnite.id !== undefined) {
      this.subscribeToSaveResponse(this.produitUniteService.update(produitUnite));
    } else {
      this.subscribeToSaveResponse(this.produitUniteService.create(produitUnite));
    }
  }

  private createFromForm(): IProduitUnite {
    return {
      ...new ProduitUnite(),
      id: this.editForm.get(['id'])!.value,
      prdunite: this.editForm.get(['prdunite'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduitUnite>>): void {
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
