import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommandeLignes, CommandeLignes } from 'app/shared/model/commande-lignes.model';
import { CommandeLignesService } from './commande-lignes.service';
import { CommandeLignesComponent } from './commande-lignes.component';
import { CommandeLignesDetailComponent } from './commande-lignes-detail.component';
import { CommandeLignesUpdateComponent } from './commande-lignes-update.component';

@Injectable({ providedIn: 'root' })
export class CommandeLignesResolve implements Resolve<ICommandeLignes> {
  constructor(private service: CommandeLignesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommandeLignes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commandeLignes: HttpResponse<CommandeLignes>) => {
          if (commandeLignes.body) {
            return of(commandeLignes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommandeLignes());
  }
}

export const commandeLignesRoute: Routes = [
  {
    path: '',
    component: CommandeLignesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.commandeLignes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CommandeLignesDetailComponent,
    resolve: {
      commandeLignes: CommandeLignesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.commandeLignes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CommandeLignesUpdateComponent,
    resolve: {
      commandeLignes: CommandeLignesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.commandeLignes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CommandeLignesUpdateComponent,
    resolve: {
      commandeLignes: CommandeLignesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazarv3App.commandeLignes.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
