import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILivraison, Livraison } from 'app/shared/model/livraison.model';
import { LivraisonService } from './livraison.service';
import { LivraisonComponent } from './livraison.component';
import { LivraisonDetailComponent } from './livraison-detail.component';
import { LivraisonUpdateComponent } from './livraison-update.component';

@Injectable({ providedIn: 'root' })
export class LivraisonResolve implements Resolve<ILivraison> {
  constructor(private service: LivraisonService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILivraison> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((livraison: HttpResponse<Livraison>) => {
          if (livraison.body) {
            return of(livraison.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Livraison());
  }
}

export const livraisonRoute: Routes = [
  {
    path: '',
    component: LivraisonComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.livraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LivraisonDetailComponent,
    resolve: {
      livraison: LivraisonResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.livraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LivraisonUpdateComponent,
    resolve: {
      livraison: LivraisonResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.livraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LivraisonUpdateComponent,
    resolve: {
      livraison: LivraisonResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.livraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
