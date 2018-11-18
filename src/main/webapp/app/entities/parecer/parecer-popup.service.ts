import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Parecer } from './parecer.model';
import { ParecerService } from './parecer.service';

@Injectable()
export class ParecerPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private parecerService: ParecerService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.parecerService.find(id)
                    .subscribe((parecerResponse: HttpResponse<Parecer>) => {
                        const parecer: Parecer = parecerResponse.body;
                        parecer.dataCadastro = this.datePipe
                            .transform(parecer.dataCadastro, 'yyyy-MM-ddTHH:mm:ss');
                        parecer.dataAtualizacao = this.datePipe
                            .transform(parecer.dataAtualizacao, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.parecerModalRef(component, parecer);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.parecerModalRef(component, new Parecer());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    parecerModalRef(component: Component, parecer: Parecer): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.parecer = parecer;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
