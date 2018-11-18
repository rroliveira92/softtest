import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Parecer } from './parecer.model';
import { ParecerService } from './parecer.service';

@Component({
    selector: 'jhi-parecer-detail',
    templateUrl: './parecer-detail.component.html'
})
export class ParecerDetailComponent implements OnInit, OnDestroy {

    parecer: Parecer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private parecerService: ParecerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInParecers();
    }

    load(id) {
        this.parecerService.find(id)
            .subscribe((parecerResponse: HttpResponse<Parecer>) => {
                this.parecer = parecerResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInParecers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'parecerListModification',
            (response) => this.load(this.parecer.id)
        );
    }
}
