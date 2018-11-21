import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Parecer } from './parecer.model';
import { ParecerService } from './parecer.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';

@Component({
    selector: 'jhi-parecer',
    templateUrl: './parecer.component.html'
})
export class ParecerComponent implements OnInit, OnDestroy {

currentAccount: any;
    parecers: Parecer[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    criteria: any;

    constructor(
        private parecerService: ParecerService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
            this.criteria = {
                idParecer: null,
                parecer: null,
                areSet() {
                    return this.idParecer != null || this.parecer != null;
                },
                clear() {
                    this.idParecer = null;
                    this.parecer = null;
                }
            };
        });
    }

    search(criteria) {
        if (criteria.areSet()) {
            this.parecers = [];
            this.loadAll();
        }
    }

    loadAll() {
        const criteria = [];

        if (this.criteria.areSet()) {
            if (this.criteria.idParecer != null) {
                criteria.push({key: 'id.equals', value: this.criteria.idParecer});
            }
            if (this.criteria.parecer) {
                criteria.push({key: 'parecer.contains', value: this.criteria.parecer});
            }
        }
        this.parecerService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort(),
            criteria
        }).subscribe(
                (res: HttpResponse<Parecer[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/parecer'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.criteria.clear();
        this.router.navigate(['/parecer', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInParecers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Parecer) {
        return item.id;
    }
    registerChangeInParecers() {
        this.eventSubscriber = this.eventManager.subscribe('parecerListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.parecers = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
