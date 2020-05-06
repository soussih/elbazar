import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProduitUnite, ProduitUnite } from 'app/shared/model/produit-unite.model';
import { ProduitUniteService } from './produit-unite.service';
import { ProduitUniteComponent } from './produit-unite.component';
import { ProduitUniteDetailComponent } from './produit-unite-detail.component';
import { ProduitUniteUpdateComponent } from './produit-unite-update.component';

@Injectable({ providedIn: 'root' })
export class ProduitUniteResolve implements Resolve<IProduitUnite> {
  constructor(private service: ProduitUniteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProduitUnite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((produitUnite: HttpResponse<ProduitUnite>) => {
          if (produitUnite.body) {
            return of(produitUnite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProduitUnite());
  }
}

export const produitUniteRoute: Routes = [
  {
    path: '',
    component: ProduitUniteComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.produitUnite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProduitUniteDetailComponent,
    resolve: {
      produitUnite: ProduitUniteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.produitUnite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProduitUniteUpdateComponent,
    resolve: {
      produitUnite: ProduitUniteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.produitUnite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProduitUniteUpdateComponent,
    resolve: {
      produitUnite: ProduitUniteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.produitUnite.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
