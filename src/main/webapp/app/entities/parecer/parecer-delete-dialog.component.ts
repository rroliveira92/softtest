import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Parecer } from './parecer.model';
import { ParecerPopupService } from './parecer-popup.service';
import { ParecerService } from './parecer.service';

@Component({
    selector: 'jhi-parecer-delete-dialog',
    templateUrl: './parecer-delete-dialog.component.html'
})
export class ParecerDeleteDialogComponent {

    parecer: Parecer;

    constructor(
        private parecerService: ParecerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.parecerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'parecerListModification',
                content: 'Deleted an parecer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-parecer-delete-popup',
    template: ''
})
export class ParecerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parecerPopupService: ParecerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.parecerPopupService
                .open(ParecerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
