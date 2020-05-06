import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProdEnumerationService } from 'app/entities/prod-enumeration/prod-enumeration.service';
import { IProdEnumeration, ProdEnumeration } from 'app/shared/model/prod-enumeration.model';
import { ProdEnum } from 'app/shared/model/enumerations/prod-enum.model';

describe('Service Tests', () => {
  describe('ProdEnumeration Service', () => {
    let injector: TestBed;
    let service: ProdEnumerationService;
    let httpMock: HttpTestingController;
    let elemDefault: IProdEnumeration;
    let expectedResult: IProdEnumeration | IProdEnumeration[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProdEnumerationService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ProdEnumeration(0, ProdEnum.Marque, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ProdEnumeration', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ProdEnumeration()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ProdEnumeration', () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            nom: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ProdEnumeration', () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            nom: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ProdEnumeration', () => {
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
