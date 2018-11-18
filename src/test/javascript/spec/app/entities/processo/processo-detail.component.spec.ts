/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SofttestTestModule } from '../../../test.module';
import { ProcessoDetailComponent } from '../../../../../../main/webapp/app/entities/processo/processo-detail.component';
import { ProcessoService } from '../../../../../../main/webapp/app/entities/processo/processo.service';
import { Processo } from '../../../../../../main/webapp/app/entities/processo/processo.model';

describe('Component Tests', () => {

    describe('Processo Management Detail Component', () => {
        let comp: ProcessoDetailComponent;
        let fixture: ComponentFixture<ProcessoDetailComponent>;
        let service: ProcessoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SofttestTestModule],
                declarations: [ProcessoDetailComponent],
                providers: [
                    ProcessoService
                ]
            })
            .overrideTemplate(ProcessoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProcessoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcessoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Processo(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.processo).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
