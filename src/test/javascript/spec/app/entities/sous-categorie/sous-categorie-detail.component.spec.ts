import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Bazarv3TestModule } from '../../../test.module';
import { SousCategorieDetailComponent } from 'app/entities/sous-categorie/sous-categorie-detail.component';
import { SousCategorie } from 'app/shared/model/sous-categorie.model';

describe('Component Tests', () => {
  describe('SousCategorie Management Detail Component', () => {
    let comp: SousCategorieDetailComponent;
    let fixture: ComponentFixture<SousCategorieDetailComponent>;
    const route = ({ data: of({ sousCategorie: new SousCategorie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [SousCategorieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SousCategorieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SousCategorieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sousCategorie on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sousCategorie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
