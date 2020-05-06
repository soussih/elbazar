import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CommandeService } from 'app/entities/commande/commande.service';
import { ICommande, Commande } from 'app/shared/model/commande.model';
import { StatCmd } from 'app/shared/model/enumerations/stat-cmd.model';
import { NaturePiece } from 'app/shared/model/enumerations/nature-piece.model';

describe('Service Tests', () => {
  describe('Commande Service', () => {
    let injector: TestBed;
    let service: CommandeService;
    let httpMock: HttpTestingController;
    let elemDefault: ICommande;
    let expectedResult: ICommande | ICommande[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CommandeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Commande(
        0,
        'AAAAAAA',
        currentDate,
        StatCmd.Commande,
        NaturePiece.Commande,
        0,
        0,
        0,
        0,
        'AAAAAAA',
        currentDate,
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
            date: currentDate.format(DATE_FORMAT),
            dateLivraison: currentDate.format(DATE_FORMAT),
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

      it('should create a Commande', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_FORMAT),
            dateLivraison: currentDate.format(DATE_FORMAT),
            creeLe: currentDate.format(DATE_FORMAT),
            modifieLe: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            dateLivraison: currentDate,
            creeLe: currentDate,
            modifieLe: currentDate
          },
          returnedFromService
        );

        service.create(new Commande()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Commande', () => {
        const returnedFromService = Object.assign(
          {
            reference: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            statut: 'BBBBBB',
            naturePiece: 'BBBBBB',
            totalHT: 1,
            totalTVA: 1,
            totalRemise: 1,
            totTTC: 1,
            zone: 'BBBBBB',
            dateLivraison: currentDate.format(DATE_FORMAT),
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
            dateLivraison: currentDate,
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

      it('should return a list of Commande', () => {
        const returnedFromService = Object.assign(
          {
            reference: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            statut: 'BBBBBB',
            naturePiece: 'BBBBBB',
            totalHT: 1,
            totalTVA: 1,
            totalRemise: 1,
            totTTC: 1,
            zone: 'BBBBBB',
            dateLivraison: currentDate.format(DATE_FORMAT),
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
            dateLivraison: currentDate,
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

      it('should delete a Commande', () => {
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
