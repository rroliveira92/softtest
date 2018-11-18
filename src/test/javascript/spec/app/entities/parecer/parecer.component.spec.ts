/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SofttestTestModule } from '../../../test.module';
import { ParecerComponent } from '../../../../../../main/webapp/app/entities/parecer/parecer.component';
import { ParecerService } from '../../../../../../main/webapp/app/entities/parecer/parecer.service';
import { Parecer } from '../../../../../../main/webapp/app/entities/parecer/parecer.model';

describe('Component Tests', () => {

    describe('Parecer Management Component', () => {
        let comp: ParecerComponent;
        let fixture: ComponentFixture<ParecerComponent>;
        let service: ParecerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SofttestTestModule],
                declarations: [ParecerComponent],
                providers: [
                    ParecerService
                ]
            })
            .overrideTemplate(ParecerComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParecerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParecerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Parecer(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.parecers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
