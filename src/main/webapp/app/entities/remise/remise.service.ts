import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IRemise } from 'app/shared/model/remise.model';

type EntityResponseType = HttpResponse<IRemise>;
type EntityArrayResponseType = HttpResponse<IRemise[]>;

@Injectable({ providedIn: 'root' })
export class RemiseService {
  public resourceUrl = SERVER_API_URL + 'api/remises';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/remises';

  constructor(protected http: HttpClient) {}

  create(remise: IRemise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(remise);
    return this.http
      .post<IRemise>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(remise: IRemise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(remise);
    return this.http
      .put<IRemise>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRemise>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRemise[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRemise[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(remise: IRemise): IRemise {
    const copy: IRemise = Object.assign({}, remise, {
      debutPromo: remise.debutPromo && remise.debutPromo.isValid() ? remise.debutPromo.format(DATE_FORMAT) : undefined,
      finPromo: remise.finPromo && remise.finPromo.isValid() ? remise.finPromo.format(DATE_FORMAT) : undefined,
      creeLe: remise.creeLe && remise.creeLe.isValid() ? remise.creeLe.format(DATE_FORMAT) : undefined,
      modifieLe: remise.modifieLe && remise.modifieLe.isValid() ? remise.modifieLe.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.debutPromo = res.body.debutPromo ? moment(res.body.debutPromo) : undefined;
      res.body.finPromo = res.body.finPromo ? moment(res.body.finPromo) : undefined;
      res.body.creeLe = res.body.creeLe ? moment(res.body.creeLe) : undefined;
      res.body.modifieLe = res.body.modifieLe ? moment(res.body.modifieLe) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((remise: IRemise) => {
        remise.debutPromo = remise.debutPromo ? moment(remise.debutPromo) : undefined;
        remise.finPromo = remise.finPromo ? moment(remise.finPromo) : undefined;
        remise.creeLe = remise.creeLe ? moment(remise.creeLe) : undefined;
        remise.modifieLe = remise.modifieLe ? moment(remise.modifieLe) : undefined;
      });
    }
    return res;
  }
}
