package com.br.se.r92.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Processo.
 */
@Entity
@Table(name = "tb_processo")
@EntityListeners(AuditingEntityListener.class)
public class Processo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Processo")
    @SequenceGenerator(name = "Processo", allocationSize = 1, initialValue = 1, sequenceName = "sq_processo")
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @CreatedDate
    @Column(name = "data_cadastro", nullable = false)
    private Instant dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private Instant dataAtualizacao;

    @ManyToOne
    private User usuarioCadastro;

    @ManyToOne
    private User usuarioAtualizacao;

    @ManyToMany
    @JoinTable(name = "processo_usuarios_parecer",
               joinColumns = @JoinColumn(name="processos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="usuarios_parecers_id", referencedColumnName="id"))
    private Set<User> usuariosParecers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Processo descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public Processo dataCadastro(Instant dataCadastro) {
        this.dataCadastro = dataCadastro;
        return this;
    }

    public void setDataCadastro(Instant dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Instant getDataAtualizacao() {
        return dataAtualizacao;
    }

    public Processo dataAtualizacao(Instant dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
        return this;
    }

    public void setDataAtualizacao(Instant dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public User getUsuarioCadastro() {
        return usuarioCadastro;
    }

    public Processo usuarioCadastro(User user) {
        this.usuarioCadastro = user;
        return this;
    }

    public void setUsuarioCadastro(User user) {
        this.usuarioCadastro = user;
    }

    public User getUsuarioAtualizacao() {
        return usuarioAtualizacao;
    }

    public Processo usuarioAtualizacao(User user) {
        this.usuarioAtualizacao = user;
        return this;
    }

    public void setUsuarioAtualizacao(User user) {
        this.usuarioAtualizacao = user;
    }

    public Set<User> getUsuariosParecers() {
        return usuariosParecers;
    }

    public Processo usuariosParecers(Set<User> users) {
        this.usuariosParecers = users;
        return this;
    }

    public Processo addUsuariosParecer(User user) {
        this.usuariosParecers.add(user);
        return this;
    }

    public Processo removeUsuariosParecer(User user) {
        this.usuariosParecers.remove(user);
        return this;
    }

    public void setUsuariosParecers(Set<User> users) {
        this.usuariosParecers = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Processo processo = (Processo) o;
        if (processo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), processo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Processo{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", dataCadastro='" + getDataCadastro() + "'" +
            ", dataAtualizacao='" + getDataAtualizacao() + "'" +
            "}";
    }
}
