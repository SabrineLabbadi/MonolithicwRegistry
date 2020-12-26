import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConcert, Concert } from 'app/shared/model/concert.model';
import { ConcertService } from './concert.service';
import { ConcertComponent } from './concert.component';
import { ConcertDetailComponent } from './concert-detail.component';
import { ConcertUpdateComponent } from './concert-update.component';

@Injectable({ providedIn: 'root' })
export class ConcertResolve implements Resolve<IConcert> {
  constructor(private service: ConcertService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConcert> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((concert: HttpResponse<Concert>) => {
          if (concert.body) {
            return of(concert.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Concert());
  }
}

export const concertRoute: Routes = [
  {
    path: '',
    component: ConcertComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'monolithicwRegistryApp.concert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConcertDetailComponent,
    resolve: {
      concert: ConcertResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'monolithicwRegistryApp.concert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConcertUpdateComponent,
    resolve: {
      concert: ConcertResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'monolithicwRegistryApp.concert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConcertUpdateComponent,
    resolve: {
      concert: ConcertResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'monolithicwRegistryApp.concert.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
