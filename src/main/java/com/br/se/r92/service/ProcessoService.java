package com.br.se.r92.service;

import java.util.List;

import com.br.se.r92.domain.Parecer;
import com.br.se.r92.domain.Processo;
import com.br.se.r92.domain.User;
import com.br.se.r92.repository.ProcessoRepository;
import com.br.se.r92.repository.UserRepository;
import com.br.se.r92.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Processo.
 */
@Service
@Transactional
public class ProcessoService {

    private final Logger log = LoggerFactory.getLogger(ProcessoService.class);

    private final ProcessoRepository processoRepository;
    private final ParecerService parecerService;
    private final UserRepository userRepository;

    public ProcessoService(ProcessoRepository processoRepository, ParecerService parecerService, UserRepository userRepository) {
        this.processoRepository = processoRepository;
        this.userRepository = userRepository;
        this.parecerService = parecerService;
    }

    /**
     * Save a processo.
     *
     * @param processo the entity to save
     * @return the persisted entity
     */
    public Processo save(Processo processo) {
        log.debug("Request to save Processo : {}", processo);
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        if (processo.getId() == null) {
        	processo.setUsuarioCadastro(user);
        	processo.setUsuarioAtualizacao(user);
        } else {
        	processo.setUsuarioAtualizacao(user);
        }
        return processoRepository.save(processo);
    }

    /**
     * Get all the processos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Processo> findAll(Pageable pageable) {
        log.debug("Request to get all Processos");
        return processoRepository.findAll(pageable);
    }

    /**
     * Get one processo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Processo findOne(Long id) {
        log.debug("Request to get Processo : {}", id);
        return processoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the processo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Processo : {}", id);
        parecerService.getAllByProcesso(id).stream().forEach(par -> parecerService.delete(par.getId()));
        processoRepository.delete(id);
    }
}
