import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SofttestProcessoModule } from './processo/processo.module';
import { SofttestParecerModule } from './parecer/parecer.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SofttestProcessoModule,
        SofttestParecerModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SofttestEntityModule {}
