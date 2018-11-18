package com.br.se.r92.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the Parecer entity. This class is used in ParecerResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /parecers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParecerCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter parecer;

    private InstantFilter dataCadastro;

    private InstantFilter dataAtualizacao;

    private LongFilter usuarioId;

    private LongFilter processoId;

    public ParecerCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getParecer() {
        return parecer;
    }

    public void setParecer(StringFilter parecer) {
        this.parecer = parecer;
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

    public LongFilter getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(LongFilter usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LongFilter getProcessoId() {
        return processoId;
    }

    public void setProcessoId(LongFilter processoId) {
        this.processoId = processoId;
    }

    @Override
    public String toString() {
        return "ParecerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (parecer != null ? "parecer=" + parecer + ", " : "") +
                (dataCadastro != null ? "dataCadastro=" + dataCadastro + ", " : "") +
                (dataAtualizacao != null ? "dataAtualizacao=" + dataAtualizacao + ", " : "") +
                (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
                (processoId != null ? "processoId=" + processoId + ", " : "") +
            "}";
    }

}
