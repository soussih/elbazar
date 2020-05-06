import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Bazarv3TestModule } from '../../../test.module';
import { ProdEnumerationDetailComponent } from 'app/entities/prod-enumeration/prod-enumeration-detail.component';
import { ProdEnumeration } from 'app/shared/model/prod-enumeration.model';

describe('Component Tests', () => {
  describe('ProdEnumeration Management Detail Component', () => {
    let comp: ProdEnumerationDetailComponent;
    let fixture: ComponentFixture<ProdEnumerationDetailComponent>;
    const route = ({ data: of({ prodEnumeration: new ProdEnumeration(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [ProdEnumerationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProdEnumerationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProdEnumerationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load prodEnumeration on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prodEnumeration).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
