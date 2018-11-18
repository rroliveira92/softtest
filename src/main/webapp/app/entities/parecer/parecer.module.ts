import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SofttestSharedModule } from '../../shared';
import { SofttestAdminModule } from '../../admin/admin.module';
import {
    ParecerService,
    ParecerPopupService,
    ParecerComponent,
    ParecerDetailComponent,
    ParecerDialogComponent,
    ParecerPopupComponent,
    ParecerDeletePopupComponent,
    ParecerDeleteDialogComponent,
    parecerRoute,
    parecerPopupRoute,
    ParecerResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...parecerRoute,
    ...parecerPopupRoute,
];

@NgModule({
    imports: [
        SofttestSharedModule,
        SofttestAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ParecerComponent,
        ParecerDetailComponent,
        ParecerDialogComponent,
        ParecerDeleteDialogComponent,
        ParecerPopupComponent,
        ParecerDeletePopupComponent,
    ],
    entryComponents: [
        ParecerComponent,
        ParecerDialogComponent,
        ParecerPopupComponent,
        ParecerDeleteDialogComponent,
        ParecerDeletePopupComponent,
    ],
    providers: [
        ParecerService,
        ParecerPopupService,
        ParecerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SofttestParecerModule {}
