import { BaseEntity, User } from './../../shared';

export class Parecer implements BaseEntity {
    constructor(
        public id?: number,
        public parecer?: string,
        public dataCadastro?: any,
        public dataAtualizacao?: any,
        public usuario?: User,
        public processo?: BaseEntity,
    ) {
    }
}
