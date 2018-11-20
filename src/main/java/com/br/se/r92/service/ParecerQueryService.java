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
import io.github.jhipster.service.filter.LongFilter;

import com.br.se.r92.domain.Parecer;
import com.br.se.r92.domain.*; // for static metamodels
import com.br.se.r92.repository.ParecerRepository;
import com.br.se.r92.repository.UserRepository;
import com.br.se.r92.security.SecurityUtils;
import com.br.se.r92.service.dto.ParecerCriteria;


/**
 * Service for executing complex queries for Parecer entities in the database.
 * The main input is a {@link ParecerCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Parecer} or a {@link Page} of {@link Parecer} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParecerQueryService extends QueryService<Parecer> {

    private final Logger log = LoggerFactory.getLogger(ParecerQueryService.class);


    private final ParecerRepository parecerRepository;
    private final UserRepository userRepository;

    public ParecerQueryService(ParecerRepository parecerRepository, UserRepository userRepository) {
        this.parecerRepository = parecerRepository;
        this.userRepository = userRepository;
    }

    /**
     * Return a {@link List} of {@link Parecer} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Parecer> findByCriteria(ParecerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Parecer> specification = createSpecification(criteria);
        return parecerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Parecer} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Parecer> findByCriteria(ParecerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();        
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(user.getId());
        criteria.setUsuarioId(longFilter);
        final Specifications<Parecer> specification = createSpecification(criteria);
        return parecerRepository.findAll(specification, page);
    }

    /**
     * Function to convert ParecerCriteria to a {@link Specifications}
     */
    private Specifications<Parecer> createSpecification(ParecerCriteria criteria) {
        Specifications<Parecer> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Parecer_.id));
            }
            if (criteria.getParecer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParecer(), Parecer_.parecer));
            }
            if (criteria.getDataCadastro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataCadastro(), Parecer_.dataCadastro));
            }
            if (criteria.getDataAtualizacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAtualizacao(), Parecer_.dataAtualizacao));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUsuarioId(), Parecer_.usuario, User_.id));
            }
            if (criteria.getProcessoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getProcessoId(), Parecer_.processo, Processo_.id));
            }
        }
        return specification;
    }

}
