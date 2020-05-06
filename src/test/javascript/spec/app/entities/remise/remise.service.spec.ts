import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { RemiseService } from 'app/entities/remise/remise.service';
import { IRemise, Remise } from 'app/shared/model/remise.model';
import { CatClient } from 'app/shared/model/enumerations/cat-client.model';

describe('Service Tests', () => {
  describe('Remise Service', () => {
    let injector: TestBed;
    let service: RemiseService;
    let httpMock: HttpTestingController;
    let elemDefault: IRemise;
    let expectedResult: IRemise | IRemise[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RemiseService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Remise(0, CatClient.Silver, 0, 0, 0, false, currentDate, currentDate, currentDate, 0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            debutPromo: currentDate.format(DATE_FORMAT),
            finPromo: currentDate.format(DATE_FORMAT),
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

      it('should create a Remise', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            debutPromo: currentDate.format(DATE_FORMAT),
            finPromo: currentDate.format(DATE_FORMAT),
            creeLe: currentDate.format(DATE_FORMAT),
            modifieLe: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            debutPromo: currentDate,
            finPromo: currentDate,
            creeLe: currentDate,
            modifieLe: currentDate
          },
          returnedFromService
        );

        service.create(new Remise()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Remise', () => {
        const returnedFromService = Object.assign(
          {
            categorieClient: 'BBBBBB',
            prixUnitaire: 1,
            remiseCategorie: 1,
            remisePromo: 1,
            cumulable: true,
            debutPromo: currentDate.format(DATE_FORMAT),
            finPromo: currentDate.format(DATE_FORMAT),
            creeLe: currentDate.format(DATE_FORMAT),
            creePar: 1,
            modifieLe: currentDate.format(DATE_FORMAT),
            modifiePar: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            debutPromo: currentDate,
            finPromo: currentDate,
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

      it('should return a list of Remise', () => {
        const returnedFromService = Object.assign(
          {
            categorieClient: 'BBBBBB',
            prixUnitaire: 1,
            remiseCategorie: 1,
            remisePromo: 1,
            cumulable: true,
            debutPromo: currentDate.format(DATE_FORMAT),
            finPromo: currentDate.format(DATE_FORMAT),
            creeLe: currentDate.format(DATE_FORMAT),
            creePar: 1,
            modifieLe: currentDate.format(DATE_FORMAT),
            modifiePar: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            debutPromo: currentDate,
            finPromo: currentDate,
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

      it('should delete a Remise', () => {
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
