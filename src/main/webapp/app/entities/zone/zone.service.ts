import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IZone } from 'app/shared/model/zone.model';

type EntityResponseType = HttpResponse<IZone>;
type EntityArrayResponseType = HttpResponse<IZone[]>;

@Injectable({ providedIn: 'root' })
export class ZoneService {
  public resourceUrl = SERVER_API_URL + 'api/zones';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/zones';

  constructor(protected http: HttpClient) {}

  create(zone: IZone): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(zone);
    return this.http
      .post<IZone>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(zone: IZone): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(zone);
    return this.http
      .put<IZone>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IZone>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IZone[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IZone[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(zone: IZone): IZone {
    const copy: IZone = Object.assign({}, zone, {
      creeLe: zone.creeLe && zone.creeLe.isValid() ? zone.creeLe.format(DATE_FORMAT) : undefined,
      modifieLe: zone.modifieLe && zone.modifieLe.isValid() ? zone.modifieLe.format(DATE_FORMAT) : undefined
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
      res.body.forEach((zone: IZone) => {
        zone.creeLe = zone.creeLe ? moment(zone.creeLe) : undefined;
        zone.modifieLe = zone.modifieLe ? moment(zone.modifieLe) : undefined;
      });
    }
    return res;
  }
}
