import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IStock } from 'app/shared/model/stock.model';

type EntityResponseType = HttpResponse<IStock>;
type EntityArrayResponseType = HttpResponse<IStock[]>;

@Injectable({ providedIn: 'root' })
export class StockService {
  public resourceUrl = SERVER_API_URL + 'api/stocks';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/stocks';

  constructor(protected http: HttpClient) {}

  create(stock: IStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stock);
    return this.http
      .post<IStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(stock: IStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stock);
    return this.http
      .put<IStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStock>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStock[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStock[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(stock: IStock): IStock {
    const copy: IStock = Object.assign({}, stock, {
      derniereEntre: stock.derniereEntre && stock.derniereEntre.isValid() ? stock.derniereEntre.format(DATE_FORMAT) : undefined,
      derniereSortie: stock.derniereSortie && stock.derniereSortie.isValid() ? stock.derniereSortie.format(DATE_FORMAT) : undefined,
      creeLe: stock.creeLe && stock.creeLe.isValid() ? stock.creeLe.format(DATE_FORMAT) : undefined,
      modifieLe: stock.modifieLe && stock.modifieLe.isValid() ? stock.modifieLe.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.derniereEntre = res.body.derniereEntre ? moment(res.body.derniereEntre) : undefined;
      res.body.derniereSortie = res.body.derniereSortie ? moment(res.body.derniereSortie) : undefined;
      res.body.creeLe = res.body.creeLe ? moment(res.body.creeLe) : undefined;
      res.body.modifieLe = res.body.modifieLe ? moment(res.body.modifieLe) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((stock: IStock) => {
        stock.derniereEntre = stock.derniereEntre ? moment(stock.derniereEntre) : undefined;
        stock.derniereSortie = stock.derniereSortie ? moment(stock.derniereSortie) : undefined;
        stock.creeLe = stock.creeLe ? moment(stock.creeLe) : undefined;
        stock.modifieLe = stock.modifieLe ? moment(stock.modifieLe) : undefined;
      });
    }
    return res;
  }
}
