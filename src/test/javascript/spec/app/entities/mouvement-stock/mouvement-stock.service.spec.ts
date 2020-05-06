import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MouvementStockService } from 'app/entities/mouvement-stock/mouvement-stock.service';
import { IMouvementStock, MouvementStock } from 'app/shared/model/mouvement-stock.model';
import { TypeMvt } from 'app/shared/model/enumerations/type-mvt.model';

describe('Service Tests', () => {
  describe('MouvementStock Service', () => {
    let injector: TestBed;
    let service: MouvementStockService;
    let httpMock: HttpTestingController;
    let elemDefault: IMouvementStock;
    let expectedResult: IMouvementStock | IMouvementStock[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MouvementStockService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MouvementStock(0, TypeMvt.Stock, currentDate, 0, 0, currentDate, 0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_FORMAT),
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

      it('should create a MouvementStock', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_FORMAT),
            creeLe: currentDate.format(DATE_FORMAT),
            modifieLe: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            creeLe: currentDate,
            modifieLe: currentDate
          },
          returnedFromService
        );

        service.create(new MouvementStock()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MouvementStock', () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            sens: 1,
            quantite: 1,
            creeLe: currentDate.format(DATE_FORMAT),
            creePar: 1,
            modifieLe: currentDate.format(DATE_FORMAT),
            modifiePar: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
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

      it('should return a list of MouvementStock', () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            sens: 1,
            quantite: 1,
            creeLe: currentDate.format(DATE_FORMAT),
            creePar: 1,
            modifieLe: currentDate.format(DATE_FORMAT),
            modifiePar: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
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

      it('should delete a MouvementStock', () => {
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
