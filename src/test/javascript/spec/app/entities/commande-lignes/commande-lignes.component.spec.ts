import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Bazarv3TestModule } from '../../../test.module';
import { CommandeLignesComponent } from 'app/entities/commande-lignes/commande-lignes.component';
import { CommandeLignesService } from 'app/entities/commande-lignes/commande-lignes.service';
import { CommandeLignes } from 'app/shared/model/commande-lignes.model';

describe('Component Tests', () => {
  describe('CommandeLignes Management Component', () => {
    let comp: CommandeLignesComponent;
    let fixture: ComponentFixture<CommandeLignesComponent>;
    let service: CommandeLignesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [CommandeLignesComponent]
      })
        .overrideTemplate(CommandeLignesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommandeLignesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommandeLignesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommandeLignes(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commandeLignes && comp.commandeLignes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
