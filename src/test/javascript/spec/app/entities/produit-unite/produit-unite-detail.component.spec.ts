import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Bazarv3TestModule } from '../../../test.module';
import { ProduitUniteDetailComponent } from 'app/entities/produit-unite/produit-unite-detail.component';
import { ProduitUnite } from 'app/shared/model/produit-unite.model';

describe('Component Tests', () => {
  describe('ProduitUnite Management Detail Component', () => {
    let comp: ProduitUniteDetailComponent;
    let fixture: ComponentFixture<ProduitUniteDetailComponent>;
    const route = ({ data: of({ produitUnite: new ProduitUnite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [ProduitUniteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProduitUniteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProduitUniteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load produitUnite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.produitUnite).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
