import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICodePostaux } from 'app/shared/model/code-postaux.model';

type EntityResponseType = HttpResponse<ICodePostaux>;
type EntityArrayResponseType = HttpResponse<ICodePostaux[]>;

@Injectable({ providedIn: 'root' })
export class CodePostauxService {
  public resourceUrl = SERVER_API_URL + 'api/code-postauxes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/code-postauxes';

  constructor(protected http: HttpClient) {}

  create(codePostaux: ICodePostaux): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(codePostaux);
    return this.http
      .post<ICodePostaux>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(codePostaux: ICodePostaux): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(codePostaux);
    return this.http
      .put<ICodePostaux>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICodePostaux>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICodePostaux[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICodePostaux[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(codePostaux: ICodePostaux): ICodePostaux {
    const copy: ICodePostaux = Object.assign({}, codePostaux, {
      modifieLe: codePostaux.modifieLe && codePostaux.modifieLe.isValid() ? codePostaux.modifieLe.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.modifieLe = res.body.modifieLe ? moment(res.body.modifieLe) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((codePostaux: ICodePostaux) => {
        codePostaux.modifieLe = codePostaux.modifieLe ? moment(codePostaux.modifieLe) : undefined;
      });
    }
    return res;
  }
}
