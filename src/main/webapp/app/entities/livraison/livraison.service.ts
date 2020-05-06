import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ILivraison } from 'app/shared/model/livraison.model';

type EntityResponseType = HttpResponse<ILivraison>;
type EntityArrayResponseType = HttpResponse<ILivraison[]>;

@Injectable({ providedIn: 'root' })
export class LivraisonService {
  public resourceUrl = SERVER_API_URL + 'api/livraisons';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/livraisons';

  constructor(protected http: HttpClient) {}

  create(livraison: ILivraison): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(livraison);
    return this.http
      .post<ILivraison>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(livraison: ILivraison): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(livraison);
    return this.http
      .put<ILivraison>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILivraison>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILivraison[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILivraison[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(livraison: ILivraison): ILivraison {
    const copy: ILivraison = Object.assign({}, livraison, {
      creeLe: livraison.creeLe && livraison.creeLe.isValid() ? livraison.creeLe.format(DATE_FORMAT) : undefined,
      modifieLe: livraison.modifieLe && livraison.modifieLe.isValid() ? livraison.modifieLe.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creeLe = res.body.creeLe ? moment(res.body.creeLe) : undefined;
      res.body.modifieLe = res.body.modifieLe ? moment(res.body.modifieLe) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((livraison: ILivraison) => {
        livraison.creeLe = livraison.creeLe ? moment(livraison.creeLe) : undefined;
        livraison.modifieLe = livraison.modifieLe ? moment(livraison.modifieLe) : undefined;
      });
    }
    return res;
  }
}
