import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IMouvementStock } from 'app/shared/model/mouvement-stock.model';

type EntityResponseType = HttpResponse<IMouvementStock>;
type EntityArrayResponseType = HttpResponse<IMouvementStock[]>;

@Injectable({ providedIn: 'root' })
export class MouvementStockService {
  public resourceUrl = SERVER_API_URL + 'api/mouvement-stocks';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/mouvement-stocks';

  constructor(protected http: HttpClient) {}

  create(mouvementStock: IMouvementStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mouvementStock);
    return this.http
      .post<IMouvementStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(mouvementStock: IMouvementStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mouvementStock);
    return this.http
      .put<IMouvementStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMouvementStock>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMouvementStock[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMouvementStock[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(mouvementStock: IMouvementStock): IMouvementStock {
    const copy: IMouvementStock = Object.assign({}, mouvementStock, {
      date: mouvementStock.date && mouvementStock.date.isValid() ? mouvementStock.date.format(DATE_FORMAT) : undefined,
      creeLe: mouvementStock.creeLe && mouvementStock.creeLe.isValid() ? mouvementStock.creeLe.format(DATE_FORMAT) : undefined,
      modifieLe: mouvementStock.modifieLe && mouvementStock.modifieLe.isValid() ? mouvementStock.modifieLe.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.creeLe = res.body.creeLe ? moment(res.body.creeLe) : undefined;
      res.body.modifieLe = res.body.modifieLe ? moment(res.body.modifieLe) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((mouvementStock: IMouvementStock) => {
        mouvementStock.date = mouvementStock.date ? moment(mouvementStock.date) : undefined;
        mouvementStock.creeLe = mouvementStock.creeLe ? moment(mouvementStock.creeLe) : undefined;
        mouvementStock.modifieLe = mouvementStock.modifieLe ? moment(mouvementStock.modifieLe) : undefined;
      });
    }
    return res;
  }
}
