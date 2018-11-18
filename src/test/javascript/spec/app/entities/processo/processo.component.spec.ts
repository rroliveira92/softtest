/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SofttestTestModule } from '../../../test.module';
import { ProcessoComponent } from '../../../../../../main/webapp/app/entities/processo/processo.component';
import { ProcessoService } from '../../../../../../main/webapp/app/entities/processo/processo.service';
import { Processo } from '../../../../../../main/webapp/app/entities/processo/processo.model';

describe('Component Tests', () => {

    describe('Processo Management Component', () => {
        let comp: ProcessoComponent;
        let fixture: ComponentFixture<ProcessoComponent>;
        let service: ProcessoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SofttestTestModule],
                declarations: [ProcessoComponent],
                providers: [
                    ProcessoService
                ]
            })
            .overrideTemplate(ProcessoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProcessoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcessoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Processo(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.processos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
