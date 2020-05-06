import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProduitService } from 'app/entities/produit/produit.service';
import { IProduit, Produit } from 'app/shared/model/produit.model';
import { SourcePrd } from 'app/shared/model/enumerations/source-prd.model';

describe('Service Tests', () => {
  describe('Produit Service', () => {
    let injector: TestBed;
    let service: ProduitService;
    let httpMock: HttpTestingController;
    let elemDefault: IProduit;
    let expectedResult: IProduit | IProduit[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProduitService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Produit(
        0,
        'AAAAAAA',
        0,
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        currentDate,
        0,
        0,
        0,
        SourcePrd.Locale,
        false,
        'AAAAAAA',
        0,
        currentDate,
        0,
        currentDate,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            datePremption: currentDate.format(DATE_FORMAT),
            creeLe: currentDate.format(DATE_FORMAT),
            modifieLe: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Produit', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            datePremption: currentDate.format(DATE_FORMAT),
            creeLe: currentDate.format(DATE_FORMAT),
            modifieLe: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datePremption: currentDate,
            creeLe: currentDate,
            modifieLe: currentDate
          },
          returnedFromService
        );

        service.create(new Produit()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Produit', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            codeBarre: 1,
            description: 'BBBBBB',
            etat: true,
            unite: 'BBBBBB',
            marque: 'BBBBBB',
            nature: 'BBBBBB',
            stockMinimum: 1,
            quantiteVenteMax: 1,
            datePremption: currentDate.format(DATE_FORMAT),
            prixUnitaireHT: 1,
            tauxTva: 1,
            prixTtc: 1,
            sourceProduit: 'BBBBBB',
            horsStock: true,
            rating: 'BBBBBB',
            remise: 1,
            creeLe: currentDate.format(DATE_FORMAT),
            creePar: 1,
            modifieLe: currentDate.format(DATE_FORMAT),
            modifiePar: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datePremption: currentDate,
            creeLe: currentDate,
            modifieLe: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Produit', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            codeBarre: 1,
            description: 'BBBBBB',
            etat: true,
            unite: 'BBBBBB',
            marque: 'BBBBBB',
            nature: 'BBBBBB',
            stockMinimum: 1,
            quantiteVenteMax: 1,
            datePremption: currentDate.format(DATE_FORMAT),
            prixUnitaireHT: 1,
            tauxTva: 1,
            prixTtc: 1,
            sourceProduit: 'BBBBBB',
            horsStock: true,
            rating: 'BBBBBB',
            remise: 1,
            creeLe: currentDate.format(DATE_FORMAT),
            creePar: 1,
            modifieLe: currentDate.format(DATE_FORMAT),
            modifiePar: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datePremption: currentDate,
            creeLe: currentDate,
            modifieLe: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Produit', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
