import { BaseEntity, User } from './../../shared';

export class Processo implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string,
        public dataCadastro?: any,
        public dataAtualizacao?: any,
        public usuarioCadastro?: User,
        public usuarioAtualizacao?: User,
        public usuariosParecers?: User[],
    ) {
    }
}
