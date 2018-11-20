import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ParecerComponent } from './parecer.component';
import { ParecerDetailComponent } from './parecer-detail.component';
import { ParecerPopupComponent } from './parecer-dialog.component';
import { ParecerDeletePopupComponent } from './parecer-delete-dialog.component';

@Injectable()
export class ParecerResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const parecerRoute: Routes = [
    {
        path: 'parecer',
        component: ParecerComponent,
        resolve: {
            'pagingParams': ParecerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_FINALIZADOR'],
            pageTitle: 'softtestApp.parecer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'parecer/:id',
        component: ParecerDetailComponent,
        data: {
            authorities: ['ROLE_FINALIZADOR'],
            pageTitle: 'softtestApp.parecer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const parecerPopupRoute: Routes = [
    {
        path: 'parecer-new',
        component: ParecerPopupComponent,
        data: {
            authorities: ['ROLE_FINALIZADOR'],
            pageTitle: 'softtestApp.parecer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parecer/:id/edit',
        component: ParecerPopupComponent,
        data: {
            authorities: ['ROLE_FINALIZADOR'],
            pageTitle: 'softtestApp.parecer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parecer/:id/delete',
        component: ParecerDeletePopupComponent,
        data: {
            authorities: ['ROLE_FINALIZADOR'],
            pageTitle: 'softtestApp.parecer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
