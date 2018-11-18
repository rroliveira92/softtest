import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Parecer } from './parecer.model';
import { ParecerPopupService } from './parecer-popup.service';
import { ParecerService } from './parecer.service';
import { User, UserService } from '../../shared';
import { Processo, ProcessoService } from '../processo';

@Component({
    selector: 'jhi-parecer-dialog',
    templateUrl: './parecer-dialog.component.html'
})
export class ParecerDialogComponent implements OnInit {

    parecer: Parecer;
    isSaving: boolean;

    users: User[];

    processos: Processo[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private parecerService: ParecerService,
        private userService: UserService,
        private processoService: ProcessoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.processoService.query()
            .subscribe((res: HttpResponse<Processo[]>) => { this.processos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.parecer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.parecerService.update(this.parecer));
        } else {
            this.subscribeToSaveResponse(
                this.parecerService.create(this.parecer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Parecer>>) {
        result.subscribe((res: HttpResponse<Parecer>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Parecer) {
        this.eventManager.broadcast({ name: 'parecerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackProcessoById(index: number, item: Processo) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-parecer-popup',
    template: ''
})
export class ParecerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parecerPopupService: ParecerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.parecerPopupService
                    .open(ParecerDialogComponent as Component, params['id']);
            } else {
                this.parecerPopupService
                    .open(ParecerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
