import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Bazarv3TestModule } from '../../../test.module';
import { MouvementStockDetailComponent } from 'app/entities/mouvement-stock/mouvement-stock-detail.component';
import { MouvementStock } from 'app/shared/model/mouvement-stock.model';

describe('Component Tests', () => {
  describe('MouvementStock Management Detail Component', () => {
    let comp: MouvementStockDetailComponent;
    let fixture: ComponentFixture<MouvementStockDetailComponent>;
    const route = ({ data: of({ mouvementStock: new MouvementStock(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [MouvementStockDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MouvementStockDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MouvementStockDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mouvementStock on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mouvementStock).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
