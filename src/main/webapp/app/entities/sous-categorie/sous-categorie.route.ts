import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISousCategorie, SousCategorie } from 'app/shared/model/sous-categorie.model';
import { SousCategorieService } from './sous-categorie.service';
import { SousCategorieComponent } from './sous-categorie.component';
import { SousCategorieDetailComponent } from './sous-categorie-detail.component';
import { SousCategorieUpdateComponent } from './sous-categorie-update.component';

@Injectable({ providedIn: 'root' })
export class SousCategorieResolve implements Resolve<ISousCategorie> {
  constructor(private service: SousCategorieService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISousCategorie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sousCategorie: HttpResponse<SousCategorie>) => {
          if (sousCategorie.body) {
            return of(sousCategorie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SousCategorie());
  }
}

export const sousCategorieRoute: Routes = [
  {
    path: '',
    component: SousCategorieComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.sousCategorie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SousCategorieDetailComponent,
    resolve: {
      sousCategorie: SousCategorieResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.sousCategorie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SousCategorieUpdateComponent,
    resolve: {
      sousCategorie: SousCategorieResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.sousCategorie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SousCategorieUpdateComponent,
    resolve: {
      sousCategorie: SousCategorieResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.sousCategorie.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
