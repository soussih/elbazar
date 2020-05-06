import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Bazarv3TestModule } from '../../../test.module';
import { CodePostauxUpdateComponent } from 'app/entities/code-postaux/code-postaux-update.component';
import { CodePostauxService } from 'app/entities/code-postaux/code-postaux.service';
import { CodePostaux } from 'app/shared/model/code-postaux.model';

describe('Component Tests', () => {
  describe('CodePostaux Management Update Component', () => {
    let comp: CodePostauxUpdateComponent;
    let fixture: ComponentFixture<CodePostauxUpdateComponent>;
    let service: CodePostauxService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [CodePostauxUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CodePostauxUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CodePostauxUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CodePostauxService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CodePostaux(123);
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
        const entity = new CodePostaux();
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
