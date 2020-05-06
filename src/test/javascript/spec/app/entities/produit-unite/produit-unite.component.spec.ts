import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Bazarv3TestModule } from '../../../test.module';
import { ProduitUniteComponent } from 'app/entities/produit-unite/produit-unite.component';
import { ProduitUniteService } from 'app/entities/produit-unite/produit-unite.service';
import { ProduitUnite } from 'app/shared/model/produit-unite.model';

describe('Component Tests', () => {
  describe('ProduitUnite Management Component', () => {
    let comp: ProduitUniteComponent;
    let fixture: ComponentFixture<ProduitUniteComponent>;
    let service: ProduitUniteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [ProduitUniteComponent]
      })
        .overrideTemplate(ProduitUniteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProduitUniteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProduitUniteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProduitUnite(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.produitUnites && comp.produitUnites[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
