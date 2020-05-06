import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAdresse } from 'app/shared/model/adresse.model';

type EntityResponseType = HttpResponse<IAdresse>;
type EntityArrayResponseType = HttpResponse<IAdresse[]>;

@Injectable({ providedIn: 'root' })
export class AdresseService {
  public resourceUrl = SERVER_API_URL + 'api/adresses';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/adresses';

  constructor(protected http: HttpClient) {}

  create(adresse: IAdresse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(adresse);
    return this.http
      .post<IAdresse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(adresse: IAdresse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(adresse);
    return this.http
      .put<IAdresse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAdresse>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAdresse[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAdresse[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(adresse: IAdresse): IAdresse {
    const copy: IAdresse = Object.assign({}, adresse, {
      creeLe: adresse.creeLe && adresse.creeLe.isValid() ? adresse.creeLe.format(DATE_FORMAT) : undefined,
      modifieLe: adresse.modifieLe && adresse.modifieLe.isValid() ? adresse.modifieLe.format(DATE_FORMAT) : undefined
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
      res.body.forEach((adresse: IAdresse) => {
        adresse.creeLe = adresse.creeLe ? moment(adresse.creeLe) : undefined;
        adresse.modifieLe = adresse.modifieLe ? moment(adresse.modifieLe) : undefined;
      });
    }
    return res;
  }
}
