import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICommandeLignes } from 'app/shared/model/commande-lignes.model';

type EntityResponseType = HttpResponse<ICommandeLignes>;
type EntityArrayResponseType = HttpResponse<ICommandeLignes[]>;

@Injectable({ providedIn: 'root' })
export class CommandeLignesService {
  public resourceUrl = SERVER_API_URL + 'api/commande-lignes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/commande-lignes';

  constructor(protected http: HttpClient) {}

  create(commandeLignes: ICommandeLignes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeLignes);
    return this.http
      .post<ICommandeLignes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commandeLignes: ICommandeLignes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeLignes);
    return this.http
      .put<ICommandeLignes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommandeLignes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommandeLignes[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommandeLignes[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(commandeLignes: ICommandeLignes): ICommandeLignes {
    const copy: ICommandeLignes = Object.assign({}, commandeLignes, {
      creeLe: commandeLignes.creeLe && commandeLignes.creeLe.isValid() ? commandeLignes.creeLe.format(DATE_FORMAT) : undefined,
      modifieLe: commandeLignes.modifieLe && commandeLignes.modifieLe.isValid() ? commandeLignes.modifieLe.format(DATE_FORMAT) : undefined
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
      res.body.forEach((commandeLignes: ICommandeLignes) => {
        commandeLignes.creeLe = commandeLignes.creeLe ? moment(commandeLignes.creeLe) : undefined;
        commandeLignes.modifieLe = commandeLignes.modifieLe ? moment(commandeLignes.modifieLe) : undefined;
      });
    }
    return res;
  }
}
