import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Bazarv3TestModule } from '../../../test.module';
import { ProdEnumerationUpdateComponent } from 'app/entities/prod-enumeration/prod-enumeration-update.component';
import { ProdEnumerationService } from 'app/entities/prod-enumeration/prod-enumeration.service';
import { ProdEnumeration } from 'app/shared/model/prod-enumeration.model';

describe('Component Tests', () => {
  describe('ProdEnumeration Management Update Component', () => {
    let comp: ProdEnumerationUpdateComponent;
    let fixture: ComponentFixture<ProdEnumerationUpdateComponent>;
    let service: ProdEnumerationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [ProdEnumerationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProdEnumerationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProdEnumerationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProdEnumerationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProdEnumeration(123);
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
        const entity = new ProdEnumeration();
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
