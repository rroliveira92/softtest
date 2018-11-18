/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SofttestTestModule } from '../../../test.module';
import { ParecerDetailComponent } from '../../../../../../main/webapp/app/entities/parecer/parecer-detail.component';
import { ParecerService } from '../../../../../../main/webapp/app/entities/parecer/parecer.service';
import { Parecer } from '../../../../../../main/webapp/app/entities/parecer/parecer.model';

describe('Component Tests', () => {

    describe('Parecer Management Detail Component', () => {
        let comp: ParecerDetailComponent;
        let fixture: ComponentFixture<ParecerDetailComponent>;
        let service: ParecerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SofttestTestModule],
                declarations: [ParecerDetailComponent],
                providers: [
                    ParecerService
                ]
            })
            .overrideTemplate(ParecerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParecerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParecerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Parecer(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.parecer).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
