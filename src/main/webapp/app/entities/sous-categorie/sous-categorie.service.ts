import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ISousCategorie } from 'app/shared/model/sous-categorie.model';

type EntityResponseType = HttpResponse<ISousCategorie>;
type EntityArrayResponseType = HttpResponse<ISousCategorie[]>;

@Injectable({ providedIn: 'root' })
export class SousCategorieService {
  public resourceUrl = SERVER_API_URL + 'api/sous-categories';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/sous-categories';

  constructor(protected http: HttpClient) {}

  create(sousCategorie: ISousCategorie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sousCategorie);
    return this.http
      .post<ISousCategorie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sousCategorie: ISousCategorie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sousCategorie);
    return this.http
      .put<ISousCategorie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISousCategorie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISousCategorie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISousCategorie[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(sousCategorie: ISousCategorie): ISousCategorie {
    const copy: ISousCategorie = Object.assign({}, sousCategorie, {
      creeLe: sousCategorie.creeLe && sousCategorie.creeLe.isValid() ? sousCategorie.creeLe.format(DATE_FORMAT) : undefined,
      modifieLe: sousCategorie.modifieLe && sousCategorie.modifieLe.isValid() ? sousCategorie.modifieLe.format(DATE_FORMAT) : undefined
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
      res.body.forEach((sousCategorie: ISousCategorie) => {
        sousCategorie.creeLe = sousCategorie.creeLe ? moment(sousCategorie.creeLe) : undefined;
        sousCategorie.modifieLe = sousCategorie.modifieLe ? moment(sousCategorie.modifieLe) : undefined;
      });
    }
    return res;
  }
}
