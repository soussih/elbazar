import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Bazarv3TestModule } from '../../../test.module';
import { MouvementStockComponent } from 'app/entities/mouvement-stock/mouvement-stock.component';
import { MouvementStockService } from 'app/entities/mouvement-stock/mouvement-stock.service';
import { MouvementStock } from 'app/shared/model/mouvement-stock.model';

describe('Component Tests', () => {
  describe('MouvementStock Management Component', () => {
    let comp: MouvementStockComponent;
    let fixture: ComponentFixture<MouvementStockComponent>;
    let service: MouvementStockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [MouvementStockComponent]
      })
        .overrideTemplate(MouvementStockComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MouvementStockComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MouvementStockService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MouvementStock(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.mouvementStocks && comp.mouvementStocks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
