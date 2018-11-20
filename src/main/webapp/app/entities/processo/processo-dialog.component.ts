import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Processo } from './processo.model';
import { ProcessoPopupService } from './processo-popup.service';
import { ProcessoService } from './processo.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-processo-dialog',
    templateUrl: './processo-dialog.component.html'
})
export class ProcessoDialogComponent implements OnInit {

    processo: Processo;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private processoService: ProcessoService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.queryFinalizadores()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        console.error(this.processo);
        // this.checkPapel();
        if (this.processo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.processoService.update(this.processo));
        } else {
            this.subscribeToSaveResponse(
                this.processoService.create(this.processo));
        }
    }

    private checkPapel() {
        for (const us of this.processo.usuariosParecers) {
            console.error(typeof us.authorities);
            const papel = [us.authorities];
            us.authorities = papel;
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Processo>>) {
        result.subscribe((res: HttpResponse<Processo>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Processo) {
        this.eventManager.broadcast({ name: 'processoListModification', content: 'OK'});
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

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-processo-popup',
    template: ''
})
export class ProcessoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private processoPopupService: ProcessoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.processoPopupService
                    .open(ProcessoDialogComponent as Component, params['id']);
            } else {
                this.processoPopupService
                    .open(ProcessoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
