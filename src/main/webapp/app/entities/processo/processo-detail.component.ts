import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Processo } from './processo.model';
import { ProcessoService } from './processo.service';
import { ParecerService, Parecer } from '../parecer';

@Component({
    selector: 'jhi-processo-detail',
    templateUrl: './processo-detail.component.html'
})
export class ProcessoDetailComponent implements OnInit, OnDestroy {

    processo: Processo;
    parecers: Parecer[];
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private processoService: ProcessoService,
        private jhiAlertService: JhiAlertService,
        private route: ActivatedRoute,
        private parecerService: ParecerService
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProcessos();
    }

    loadListaParecer() {
        if (this.processo.id) {
            const criteria = [];
            criteria.push({key: 'processoId.equals', value: this.processo.id});
            this.parecerService.query({criteria}).subscribe(
                    (res: HttpResponse<Parecer[]>) => { this.parecers = res.body; },
                    (res: HttpErrorResponse) => this.onError(res.message));
        }
    }

    load(id) {
        this.processoService.find(id)
            .subscribe((processoResponse: HttpResponse<Processo>) => {
                this.processo = processoResponse.body;
                this.loadListaParecer();
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProcessos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'processoListModification',
            (response) => this.load(this.processo.id)
        );
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
