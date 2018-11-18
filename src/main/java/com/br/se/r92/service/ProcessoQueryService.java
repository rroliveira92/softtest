package com.br.se.r92.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.br.se.r92.domain.Processo;
import com.br.se.r92.domain.*; // for static metamodels
import com.br.se.r92.repository.ProcessoRepository;
import com.br.se.r92.service.dto.ProcessoCriteria;


/**
 * Service for executing complex queries for Processo entities in the database.
 * The main input is a {@link ProcessoCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Processo} or a {@link Page} of {@link Processo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProcessoQueryService extends QueryService<Processo> {

    private final Logger log = LoggerFactory.getLogger(ProcessoQueryService.class);


    private final ProcessoRepository processoRepository;

    public ProcessoQueryService(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    /**
     * Return a {@link List} of {@link Processo} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Processo> findByCriteria(ProcessoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Processo> specification = createSpecification(criteria);
        return processoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Processo} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Processo> findByCriteria(ProcessoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Processo> specification = createSpecification(criteria);
        return processoRepository.findAll(specification, page);
    }

    /**
     * Function to convert ProcessoCriteria to a {@link Specifications}
     */
    private Specifications<Processo> createSpecification(ProcessoCriteria criteria) {
        Specifications<Processo> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Processo_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Processo_.descricao));
            }
            if (criteria.getDataCadastro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataCadastro(), Processo_.dataCadastro));
            }
            if (criteria.getDataAtualizacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAtualizacao(), Processo_.dataAtualizacao));
            }
            if (criteria.getUsuarioCadastroId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUsuarioCadastroId(), Processo_.usuarioCadastro, User_.id));
            }
            if (criteria.getUsuarioAtualizacaoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUsuarioAtualizacaoId(), Processo_.usuarioAtualizacao, User_.id));
            }
            if (criteria.getUsuariosParecerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUsuariosParecerId(), Processo_.usuariosParecers, User_.id));
            }
        }
        return specification;
    }

}
