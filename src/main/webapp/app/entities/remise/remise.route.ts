import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRemise, Remise } from 'app/shared/model/remise.model';
import { RemiseService } from './remise.service';
import { RemiseComponent } from './remise.component';
import { RemiseDetailComponent } from './remise-detail.component';
import { RemiseUpdateComponent } from './remise-update.component';

@Injectable({ providedIn: 'root' })
export class RemiseResolve implements Resolve<IRemise> {
  constructor(private service: RemiseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRemise> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((remise: HttpResponse<Remise>) => {
          if (remise.body) {
            return of(remise.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Remise());
  }
}

export const remiseRoute: Routes = [
  {
    path: '',
    component: RemiseComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.remise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RemiseDetailComponent,
    resolve: {
      remise: RemiseResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.remise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RemiseUpdateComponent,
    resolve: {
      remise: RemiseResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.remise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RemiseUpdateComponent,
    resolve: {
      remise: RemiseResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.remise.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
