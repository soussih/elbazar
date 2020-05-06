import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { StockService } from 'app/entities/stock/stock.service';
import { IStock, Stock } from 'app/shared/model/stock.model';

describe('Service Tests', () => {
  describe('Stock Service', () => {
    let injector: TestBed;
    let service: StockService;
    let httpMock: HttpTestingController;
    let elemDefault: IStock;
    let expectedResult: IStock | IStock[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(StockService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Stock(0, 0, 0, 0, 0, currentDate, currentDate, false, currentDate, 0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            derniereEntre: currentDate.format(DATE_FORMAT),
            derniereSortie: currentDate.format(DATE_FORMAT),
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

      it('should create a Stock', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            derniereEntre: currentDate.format(DATE_FORMAT),
            derniereSortie: currentDate.format(DATE_FORMAT),
            creeLe: currentDate.format(DATE_FORMAT),
            modifieLe: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            derniereEntre: currentDate,
            derniereSortie: currentDate,
            creeLe: currentDate,
            modifieLe: currentDate
          },
          returnedFromService
        );

        service.create(new Stock()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Stock', () => {
        const returnedFromService = Object.assign(
          {
            stockPhysique: 1,
            stockDisponible: 1,
            stockReserve: 1,
            stockCommande: 1,
            derniereEntre: currentDate.format(DATE_FORMAT),
            derniereSortie: currentDate.format(DATE_FORMAT),
            alerteStock: true,
            creeLe: currentDate.format(DATE_FORMAT),
            creePar: 1,
            modifieLe: currentDate.format(DATE_FORMAT),
            modifiePar: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            derniereEntre: currentDate,
            derniereSortie: currentDate,
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

      it('should return a list of Stock', () => {
        const returnedFromService = Object.assign(
          {
            stockPhysique: 1,
            stockDisponible: 1,
            stockReserve: 1,
            stockCommande: 1,
            derniereEntre: currentDate.format(DATE_FORMAT),
            derniereSortie: currentDate.format(DATE_FORMAT),
            alerteStock: true,
            creeLe: currentDate.format(DATE_FORMAT),
            creePar: 1,
            modifieLe: currentDate.format(DATE_FORMAT),
            modifiePar: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            derniereEntre: currentDate,
            derniereSortie: currentDate,
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

      it('should delete a Stock', () => {
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
