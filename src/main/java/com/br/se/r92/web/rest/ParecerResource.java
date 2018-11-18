package com.br.se.r92.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.br.se.r92.domain.Parecer;
import com.br.se.r92.service.ParecerService;
import com.br.se.r92.web.rest.errors.BadRequestAlertException;
import com.br.se.r92.web.rest.util.HeaderUtil;
import com.br.se.r92.web.rest.util.PaginationUtil;
import com.br.se.r92.service.dto.ParecerCriteria;
import com.br.se.r92.service.ParecerQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Parecer.
 */
@RestController
@RequestMapping("/api")
public class ParecerResource {

    private final Logger log = LoggerFactory.getLogger(ParecerResource.class);

    private static final String ENTITY_NAME = "parecer";

    private final ParecerService parecerService;

    private final ParecerQueryService parecerQueryService;

    public ParecerResource(ParecerService parecerService, ParecerQueryService parecerQueryService) {
        this.parecerService = parecerService;
        this.parecerQueryService = parecerQueryService;
    }

    /**
     * POST  /parecers : Create a new parecer.
     *
     * @param parecer the parecer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parecer, or with status 400 (Bad Request) if the parecer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parecers")
    @Timed
    public ResponseEntity<Parecer> createParecer(@Valid @RequestBody Parecer parecer) throws URISyntaxException {
        log.debug("REST request to save Parecer : {}", parecer);
        if (parecer.getId() != null) {
            throw new BadRequestAlertException("A new parecer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Parecer result = parecerService.save(parecer);
        return ResponseEntity.created(new URI("/api/parecers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parecers : Updates an existing parecer.
     *
     * @param parecer the parecer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parecer,
     * or with status 400 (Bad Request) if the parecer is not valid,
     * or with status 500 (Internal Server Error) if the parecer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parecers")
    @Timed
    public ResponseEntity<Parecer> updateParecer(@Valid @RequestBody Parecer parecer) throws URISyntaxException {
        log.debug("REST request to update Parecer : {}", parecer);
        if (parecer.getId() == null) {
            return createParecer(parecer);
        }
        Parecer result = parecerService.save(parecer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parecer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parecers : get all the parecers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of parecers in body
     */
    @GetMapping("/parecers")
    @Timed
    public ResponseEntity<List<Parecer>> getAllParecers(ParecerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Parecers by criteria: {}", criteria);
        Page<Parecer> page = parecerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parecers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /parecers/:id : get the "id" parecer.
     *
     * @param id the id of the parecer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parecer, or with status 404 (Not Found)
     */
    @GetMapping("/parecers/{id}")
    @Timed
    public ResponseEntity<Parecer> getParecer(@PathVariable Long id) {
        log.debug("REST request to get Parecer : {}", id);
        Parecer parecer = parecerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(parecer));
    }

    /**
     * DELETE  /parecers/:id : delete the "id" parecer.
     *
     * @param id the id of the parecer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parecers/{id}")
    @Timed
    public ResponseEntity<Void> deleteParecer(@PathVariable Long id) {
        log.debug("REST request to delete Parecer : {}", id);
        parecerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
