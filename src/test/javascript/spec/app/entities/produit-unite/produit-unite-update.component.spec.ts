import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Bazarv3TestModule } from '../../../test.module';
import { ProduitUniteUpdateComponent } from 'app/entities/produit-unite/produit-unite-update.component';
import { ProduitUniteService } from 'app/entities/produit-unite/produit-unite.service';
import { ProduitUnite } from 'app/shared/model/produit-unite.model';

describe('Component Tests', () => {
  describe('ProduitUnite Management Update Component', () => {
    let comp: ProduitUniteUpdateComponent;
    let fixture: ComponentFixture<ProduitUniteUpdateComponent>;
    let service: ProduitUniteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [ProduitUniteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProduitUniteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProduitUniteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProduitUniteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProduitUnite(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProduitUnite();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
