package com.br.se.r92.service.dto;

import java.io.Serializable;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;




/**
 * Criteria class for the Processo entity. This class is used in ProcessoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /processos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProcessoCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter descricao;

    private InstantFilter dataCadastro;

    private InstantFilter dataAtualizacao;

    private LongFilter usuarioCadastroId;

    private LongFilter usuarioAtualizacaoId;

    private LongFilter usuariosParecerId;

    public ProcessoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public InstantFilter getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(InstantFilter dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public InstantFilter getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(InstantFilter dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public LongFilter getUsuarioCadastroId() {
        return usuarioCadastroId;
    }

    public void setUsuarioCadastroId(LongFilter usuarioCadastroId) {
        this.usuarioCadastroId = usuarioCadastroId;
    }

    public LongFilter getUsuarioAtualizacaoId() {
        return usuarioAtualizacaoId;
    }

    public void setUsuarioAtualizacaoId(LongFilter usuarioAtualizacaoId) {
        this.usuarioAtualizacaoId = usuarioAtualizacaoId;
    }

    public LongFilter getUsuariosParecerId() {
        return usuariosParecerId;
    }

    public void setUsuariosParecerId(LongFilter usuariosParecerId) {
        this.usuariosParecerId = usuariosParecerId;
    }

    @Override
    public String toString() {
        return "ProcessoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (dataCadastro != null ? "dataCadastro=" + dataCadastro + ", " : "") +
                (dataAtualizacao != null ? "dataAtualizacao=" + dataAtualizacao + ", " : "") +
                (usuarioCadastroId != null ? "usuarioCadastroId=" + usuarioCadastroId + ", " : "") +
                (usuarioAtualizacaoId != null ? "usuarioAtualizacaoId=" + usuarioAtualizacaoId + ", " : "") +
                (usuariosParecerId != null ? "usuariosParecerId=" + usuariosParecerId + ", " : "") +
            "}";
    }

}
