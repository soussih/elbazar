import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Bazarv3TestModule } from '../../../test.module';
import { ProdEnumerationComponent } from 'app/entities/prod-enumeration/prod-enumeration.component';
import { ProdEnumerationService } from 'app/entities/prod-enumeration/prod-enumeration.service';
import { ProdEnumeration } from 'app/shared/model/prod-enumeration.model';

describe('Component Tests', () => {
  describe('ProdEnumeration Management Component', () => {
    let comp: ProdEnumerationComponent;
    let fixture: ComponentFixture<ProdEnumerationComponent>;
    let service: ProdEnumerationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [ProdEnumerationComponent]
      })
        .overrideTemplate(ProdEnumerationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProdEnumerationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProdEnumerationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProdEnumeration(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.prodEnumerations && comp.prodEnumerations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
