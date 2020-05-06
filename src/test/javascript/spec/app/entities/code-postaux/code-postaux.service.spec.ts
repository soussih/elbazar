import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CodePostauxService } from 'app/entities/code-postaux/code-postaux.service';
import { ICodePostaux, CodePostaux } from 'app/shared/model/code-postaux.model';

describe('Service Tests', () => {
  describe('CodePostaux Service', () => {
    let injector: TestBed;
    let service: CodePostauxService;
    let httpMock: HttpTestingController;
    let elemDefault: ICodePostaux;
    let expectedResult: ICodePostaux | ICodePostaux[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CodePostauxService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CodePostaux(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            modifieLe: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CodePostaux', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            modifieLe: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            modifieLe: currentDate
          },
          returnedFromService
        );

        service.create(new CodePostaux()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CodePostaux', () => {
        const returnedFromService = Object.assign(
          {
            gouvernorat: 'BBBBBB',
            ville: 'BBBBBB',
            localite: 'BBBBBB',
            codePostal: 1,
            modifieLe: currentDate.format(DATE_FORMAT),
            modifiePar: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            modifieLe: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CodePostaux', () => {
        const returnedFromService = Object.assign(
          {
            gouvernorat: 'BBBBBB',
            ville: 'BBBBBB',
            localite: 'BBBBBB',
            codePostal: 1,
            modifieLe: currentDate.format(DATE_FORMAT),
            modifiePar: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should delete a CodePostaux', () => {
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
