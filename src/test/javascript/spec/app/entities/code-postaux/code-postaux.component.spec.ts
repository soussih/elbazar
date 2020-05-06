import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Bazarv3TestModule } from '../../../test.module';
import { CodePostauxComponent } from 'app/entities/code-postaux/code-postaux.component';
import { CodePostauxService } from 'app/entities/code-postaux/code-postaux.service';
import { CodePostaux } from 'app/shared/model/code-postaux.model';

describe('Component Tests', () => {
  describe('CodePostaux Management Component', () => {
    let comp: CodePostauxComponent;
    let fixture: ComponentFixture<CodePostauxComponent>;
    let service: CodePostauxService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [CodePostauxComponent]
      })
        .overrideTemplate(CodePostauxComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CodePostauxComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CodePostauxService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CodePostaux(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.codePostauxes && comp.codePostauxes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
