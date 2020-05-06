import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Bazarv3TestModule } from '../../../test.module';
import { CommandeLignesDetailComponent } from 'app/entities/commande-lignes/commande-lignes-detail.component';
import { CommandeLignes } from 'app/shared/model/commande-lignes.model';

describe('Component Tests', () => {
  describe('CommandeLignes Management Detail Component', () => {
    let comp: CommandeLignesDetailComponent;
    let fixture: ComponentFixture<CommandeLignesDetailComponent>;
    const route = ({ data: of({ commandeLignes: new CommandeLignes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Bazarv3TestModule],
        declarations: [CommandeLignesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CommandeLignesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommandeLignesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commandeLignes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commandeLignes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
