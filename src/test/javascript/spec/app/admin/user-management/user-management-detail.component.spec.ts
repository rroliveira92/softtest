import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SofttestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserMgmtDetailComponent } from '../../../../../../main/webapp/app/admin/user-management/user-management-detail.component';
import { UserService, User } from '../../../../../../main/webapp/app/shared';

describe('Component Tests', () => {

    describe('User Management Detail Component', () => {
        let comp: UserMgmtDetailComponent;
        let fixture: ComponentFixture<UserMgmtDetailComponent>;
        let service: UserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SofttestTestModule],
                declarations: [UserMgmtDetailComponent],
                providers: [
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({login: 'user'})
                    },
                    UserService
                ]
            })
            .overrideTemplate(UserMgmtDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMgmtDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new User(1, 'user', 'first', 'last', 'first@last.com', true, 'en', ['ROLE_ADMIN', 'ROLE_TRIADOR', 'ROLE_FINALIZADOR'], 'admin', null, null, null)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith('user');
                expect(comp.user).toEqual(jasmine.objectContaining({
                    id: 1,
                    login: 'user',
                    firstName: 'first',
                    lastName: 'last',
                    email: 'first@last.com',
                    activated: true,
                    langKey: 'en',
                    authorities: ['ROLE_ADMIN', 'ROLE_TRIADOR', 'ROLE_FINALIZADOR'],
                    createdBy: 'admin',
                    createdDate: null,
                    lastModifiedBy: null,
                    lastModifiedDate: null,
                    password: null
                }));
            });
        });
    });

});
