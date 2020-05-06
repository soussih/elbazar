import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Bazarv3TestModule } from '../../../test.module';
import { RemiseDetailComponent } from 'app/entities/remise/remise-detail.component';
import { Remise } from 'app/shared/model/remise.model';

describe('Component Tests', () => {
  describe('Remise Management Detail Component', () => {
    let comp: RemiseDetailComponent;
    let fixture: ComponentFixture<RemiseDetailComponent>;
    const route = ({ data: of({ remise: new Remise(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [RemiseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RemiseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RemiseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load remise on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.remise).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
