import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Bazarv3TestModule } from '../../../test.module';
import { CodePostauxDetailComponent } from 'app/entities/code-postaux/code-postaux-detail.component';
import { CodePostaux } from 'app/shared/model/code-postaux.model';

describe('Component Tests', () => {
  describe('CodePostaux Management Detail Component', () => {
    let comp: CodePostauxDetailComponent;
    let fixture: ComponentFixture<CodePostauxDetailComponent>;
    const route = ({ data: of({ codePostaux: new CodePostaux(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [CodePostauxDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CodePostauxDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CodePostauxDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load codePostaux on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.codePostaux).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
