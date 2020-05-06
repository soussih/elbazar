import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IImages, Images } from 'app/shared/model/images.model';
import { ImagesService } from './images.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from 'app/entities/produit/produit.service';
import { ICategorie } from 'app/shared/model/categorie.model';
import { CategorieService } from 'app/entities/categorie/categorie.service';
import { ISousCategorie } from 'app/shared/model/sous-categorie.model';
import { SousCategorieService } from 'app/entities/sous-categorie/sous-categorie.service';

type SelectableEntity = IProduit | ICategorie | ISousCategorie;

@Component({
  selector: 'jhi-images-update',
  templateUrl: './images-update.component.html'
})
export class ImagesUpdateComponent implements OnInit {
  isSaving = false;
  produits: IProduit[] = [];
  categories: ICategorie[] = [];
  souscategories: ISousCategorie[] = [];

  editForm = this.fb.group({
    id: [],
    type: [],
    image: [],
    imageContentType: [],
    idProduitId: [],
    idCategorieId: [],
    idSousCategorieId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected imagesService: ImagesService,
    protected produitService: ProduitService,
    protected categorieService: CategorieService,
    protected sousCategorieService: SousCategorieService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ images }) => {
      this.updateForm(images);

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));

      this.categorieService.query().subscribe((res: HttpResponse<ICategorie[]>) => (this.categories = res.body || []));

      this.sousCategorieService.query().subscribe((res: HttpResponse<ISousCategorie[]>) => (this.souscategories = res.body || []));
    });
  }

  updateForm(images: IImages): void {
    this.editForm.patchValue({
      id: images.id,
      type: images.type,
      image: images.image,
      imageContentType: images.imageContentType,
      idProduitId: images.idProduitId,
      idCategorieId: images.idCategorieId,
      idSousCategorieId: images.idSousCategorieId
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('bazarv3App.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const images = this.createFromForm();
    if (images.id !== undefined) {
      this.subscribeToSaveResponse(this.imagesService.update(images));
    } else {
      this.subscribeToSaveResponse(this.imagesService.create(images));
    }
  }

  private createFromForm(): IImages {
    return {
      ...new Images(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      idProduitId: this.editForm.get(['idProduitId'])!.value,
      idCategorieId: this.editForm.get(['idCategorieId'])!.value,
      idSousCategorieId: this.editForm.get(['idSousCategorieId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImages>>): void {
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
