/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SofttestTestModule } from '../../../test.module';
import { ParecerDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/parecer/parecer-delete-dialog.component';
import { ParecerService } from '../../../../../../main/webapp/app/entities/parecer/parecer.service';

describe('Component Tests', () => {

    describe('Parecer Management Delete Component', () => {
        let comp: ParecerDeleteDialogComponent;
        let fixture: ComponentFixture<ParecerDeleteDialogComponent>;
        let service: ParecerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SofttestTestModule],
                declarations: [ParecerDeleteDialogComponent],
                providers: [
                    ParecerService
                ]
            })
            .overrideTemplate(ParecerDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParecerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParecerService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
