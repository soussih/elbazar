import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IClient } from 'app/shared/model/client.model';

type EntityResponseType = HttpResponse<IClient>;
type EntityArrayResponseType = HttpResponse<IClient[]>;

@Injectable({ providedIn: 'root' })
export class ClientService {
  public resourceUrl = SERVER_API_URL + 'api/clients';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/clients';

  constructor(protected http: HttpClient) {}

  create(client: IClient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(client);
    return this.http
      .post<IClient>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(client: IClient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(client);
    return this.http
      .put<IClient>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IClient>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClient[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClient[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(client: IClient): IClient {
    const copy: IClient = Object.assign({}, client, {
      dateDeNaissance: client.dateDeNaissance && client.dateDeNaissance.isValid() ? client.dateDeNaissance.format(DATE_FORMAT) : undefined,
      derniereVisite: client.derniereVisite && client.derniereVisite.isValid() ? client.derniereVisite.format(DATE_FORMAT) : undefined,
      creeLe: client.creeLe && client.creeLe.isValid() ? client.creeLe.format(DATE_FORMAT) : undefined,
      modifieLe: client.modifieLe && client.modifieLe.isValid() ? client.modifieLe.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDeNaissance = res.body.dateDeNaissance ? moment(res.body.dateDeNaissance) : undefined;
      res.body.derniereVisite = res.body.derniereVisite ? moment(res.body.derniereVisite) : undefined;
      res.body.creeLe = res.body.creeLe ? moment(res.body.creeLe) : undefined;
      res.body.modifieLe = res.body.modifieLe ? moment(res.body.modifieLe) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((client: IClient) => {
        client.dateDeNaissance = client.dateDeNaissance ? moment(client.dateDeNaissance) : undefined;
        client.derniereVisite = client.derniereVisite ? moment(client.derniereVisite) : undefined;
        client.creeLe = client.creeLe ? moment(client.creeLe) : undefined;
        client.modifieLe = client.modifieLe ? moment(client.modifieLe) : undefined;
      });
    }
    return res;
  }
}
