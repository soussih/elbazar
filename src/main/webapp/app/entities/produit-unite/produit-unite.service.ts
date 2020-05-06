import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IProduitUnite } from 'app/shared/model/produit-unite.model';

type EntityResponseType = HttpResponse<IProduitUnite>;
type EntityArrayResponseType = HttpResponse<IProduitUnite[]>;

@Injectable({ providedIn: 'root' })
export class ProduitUniteService {
  public resourceUrl = SERVER_API_URL + 'api/produit-unites';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/produit-unites';

  constructor(protected http: HttpClient) {}

  create(produitUnite: IProduitUnite): Observable<EntityResponseType> {
    return this.http.post<IProduitUnite>(this.resourceUrl, produitUnite, { observe: 'response' });
  }

  update(produitUnite: IProduitUnite): Observable<EntityResponseType> {
    return this.http.put<IProduitUnite>(this.resourceUrl, produitUnite, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProduitUnite>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProduitUnite[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProduitUnite[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
