import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AdresseService } from 'app/entities/adresse/adresse.service';
import { IAdresse, Adresse } from 'app/shared/model/adresse.model';

describe('Service Tests', () => {
  describe('Adresse Service', () => {
    let injector: TestBed;
    let service: AdresseService;
    let httpMock: HttpTestingController;
    let elemDefault: IAdresse;
    let expectedResult: IAdresse | IAdresse[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AdresseService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Adresse(
        0,
        false,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
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

      it('should create a Adresse', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            creeLe: currentDate.format(DATE_FORMAT),
            modifieLe: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creeLe: currentDate,
            modifieLe: currentDate
          },
          returnedFromService
        );

        service.create(new Adresse()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Adresse', () => {
        const returnedFromService = Object.assign(
          {
            principale: true,
            nom: 'BBBBBB',
            prenom: 'BBBBBB',
            adresseLigne1: 1,
            adresseLigne2: 1,
            gouvernorat: 'BBBBBB',
            ville: 'BBBBBB',
            cite: 'BBBBBB',
            indication: 'BBBBBB',
            telephone: 1,
            mobile: 1,
            creeLe: currentDate.format(DATE_FORMAT),
            creePar: 1,
            modifieLe: currentDate.format(DATE_FORMAT),
            modifiePar: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should return a list of Adresse', () => {
        const returnedFromService = Object.assign(
          {
            principale: true,
            nom: 'BBBBBB',
            prenom: 'BBBBBB',
            adresseLigne1: 1,
            adresseLigne2: 1,
            gouvernorat: 'BBBBBB',
            ville: 'BBBBBB',
            cite: 'BBBBBB',
            indication: 'BBBBBB',
            telephone: 1,
            mobile: 1,
            creeLe: currentDate.format(DATE_FORMAT),
            creePar: 1,
            modifieLe: currentDate.format(DATE_FORMAT),
            modifiePar: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should delete a Adresse', () => {
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
