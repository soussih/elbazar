import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Bazarv3TestModule } from '../../../test.module';
import { CommandeLignesUpdateComponent } from 'app/entities/commande-lignes/commande-lignes-update.component';
import { CommandeLignesService } from 'app/entities/commande-lignes/commande-lignes.service';
import { CommandeLignes } from 'app/shared/model/commande-lignes.model';

describe('Component Tests', () => {
  describe('CommandeLignes Management Update Component', () => {
    let comp: CommandeLignesUpdateComponent;
    let fixture: ComponentFixture<CommandeLignesUpdateComponent>;
    let service: CommandeLignesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [CommandeLignesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CommandeLignesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommandeLignesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommandeLignesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommandeLignes(123);
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
        const entity = new CommandeLignes();
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
