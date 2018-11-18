package com.br.se.r92.service;

import com.br.se.r92.domain.Parecer;
import com.br.se.r92.repository.ParecerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Parecer.
 */
@Service
@Transactional
public class ParecerService {

    private final Logger log = LoggerFactory.getLogger(ParecerService.class);

    private final ParecerRepository parecerRepository;

    public ParecerService(ParecerRepository parecerRepository) {
        this.parecerRepository = parecerRepository;
    }

    /**
     * Save a parecer.
     *
     * @param parecer the entity to save
     * @return the persisted entity
     */
    public Parecer save(Parecer parecer) {
        log.debug("Request to save Parecer : {}", parecer);
        return parecerRepository.save(parecer);
    }

    /**
     * Get all the parecers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Parecer> findAll(Pageable pageable) {
        log.debug("Request to get all Parecers");
        return parecerRepository.findAll(pageable);
    }

    /**
     * Get one parecer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Parecer findOne(Long id) {
        log.debug("Request to get Parecer : {}", id);
        return parecerRepository.findOne(id);
    }

    /**
     * Delete the parecer by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Parecer : {}", id);
        parecerRepository.delete(id);
    }
}
