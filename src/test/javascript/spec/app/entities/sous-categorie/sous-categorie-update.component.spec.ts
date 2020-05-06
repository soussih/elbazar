import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Bazarv3TestModule } from '../../../test.module';
import { SousCategorieUpdateComponent } from 'app/entities/sous-categorie/sous-categorie-update.component';
import { SousCategorieService } from 'app/entities/sous-categorie/sous-categorie.service';
import { SousCategorie } from 'app/shared/model/sous-categorie.model';

describe('Component Tests', () => {
  describe('SousCategorie Management Update Component', () => {
    let comp: SousCategorieUpdateComponent;
    let fixture: ComponentFixture<SousCategorieUpdateComponent>;
    let service: SousCategorieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [SousCategorieUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SousCategorieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SousCategorieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SousCategorieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SousCategorie(123);
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
        const entity = new SousCategorie();
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
