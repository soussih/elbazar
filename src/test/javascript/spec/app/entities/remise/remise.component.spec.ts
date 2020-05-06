import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Bazarv3TestModule } from '../../../test.module';
import { RemiseComponent } from 'app/entities/remise/remise.component';
import { RemiseService } from 'app/entities/remise/remise.service';
import { Remise } from 'app/shared/model/remise.model';

describe('Component Tests', () => {
  describe('Remise Management Component', () => {
    let comp: RemiseComponent;
    let fixture: ComponentFixture<RemiseComponent>;
    let service: RemiseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [RemiseComponent]
      })
        .overrideTemplate(RemiseComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RemiseComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RemiseService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Remise(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.remises && comp.remises[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
