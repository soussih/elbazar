import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProdEnumeration, ProdEnumeration } from 'app/shared/model/prod-enumeration.model';
import { ProdEnumerationService } from './prod-enumeration.service';
import { ProdEnumerationComponent } from './prod-enumeration.component';
import { ProdEnumerationDetailComponent } from './prod-enumeration-detail.component';
import { ProdEnumerationUpdateComponent } from './prod-enumeration-update.component';

@Injectable({ providedIn: 'root' })
export class ProdEnumerationResolve implements Resolve<IProdEnumeration> {
  constructor(private service: ProdEnumerationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProdEnumeration> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((prodEnumeration: HttpResponse<ProdEnumeration>) => {
          if (prodEnumeration.body) {
            return of(prodEnumeration.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProdEnumeration());
  }
}

export const prodEnumerationRoute: Routes = [
  {
    path: '',
    component: ProdEnumerationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.prodEnumeration.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProdEnumerationDetailComponent,
    resolve: {
      prodEnumeration: ProdEnumerationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.prodEnumeration.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProdEnumerationUpdateComponent,
    resolve: {
      prodEnumeration: ProdEnumerationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.prodEnumeration.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProdEnumerationUpdateComponent,
    resolve: {
      prodEnumeration: ProdEnumerationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.prodEnumeration.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
