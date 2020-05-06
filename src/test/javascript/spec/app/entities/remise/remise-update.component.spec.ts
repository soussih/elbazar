import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Bazarv3TestModule } from '../../../test.module';
import { RemiseUpdateComponent } from 'app/entities/remise/remise-update.component';
import { RemiseService } from 'app/entities/remise/remise.service';
import { Remise } from 'app/shared/model/remise.model';

describe('Component Tests', () => {
  describe('Remise Management Update Component', () => {
    let comp: RemiseUpdateComponent;
    let fixture: ComponentFixture<RemiseUpdateComponent>;
    let service: RemiseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [RemiseUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RemiseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RemiseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RemiseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Remise(123);
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
        const entity = new Remise();
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
