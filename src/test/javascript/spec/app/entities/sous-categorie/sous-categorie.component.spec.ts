import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Bazarv3TestModule } from '../../../test.module';
import { SousCategorieComponent } from 'app/entities/sous-categorie/sous-categorie.component';
import { SousCategorieService } from 'app/entities/sous-categorie/sous-categorie.service';
import { SousCategorie } from 'app/shared/model/sous-categorie.model';

describe('Component Tests', () => {
  describe('SousCategorie Management Component', () => {
    let comp: SousCategorieComponent;
    let fixture: ComponentFixture<SousCategorieComponent>;
    let service: SousCategorieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [SousCategorieComponent]
      })
        .overrideTemplate(SousCategorieComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SousCategorieComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SousCategorieService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SousCategorie(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sousCategories && comp.sousCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
