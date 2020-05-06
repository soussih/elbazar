import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICodePostaux, CodePostaux } from 'app/shared/model/code-postaux.model';
import { CodePostauxService } from './code-postaux.service';
import { CodePostauxComponent } from './code-postaux.component';
import { CodePostauxDetailComponent } from './code-postaux-detail.component';
import { CodePostauxUpdateComponent } from './code-postaux-update.component';

@Injectable({ providedIn: 'root' })
export class CodePostauxResolve implements Resolve<ICodePostaux> {
  constructor(private service: CodePostauxService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICodePostaux> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((codePostaux: HttpResponse<CodePostaux>) => {
          if (codePostaux.body) {
            return of(codePostaux.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CodePostaux());
  }
}

export const codePostauxRoute: Routes = [
  {
    path: '',
    component: CodePostauxComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.codePostaux.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CodePostauxDetailComponent,
    resolve: {
      codePostaux: CodePostauxResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.codePostaux.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CodePostauxUpdateComponent,
    resolve: {
      codePostaux: CodePostauxResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.codePostaux.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CodePostauxUpdateComponent,
    resolve: {
      codePostaux: CodePostauxResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.codePostaux.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
