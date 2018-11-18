package com.br.se.r92.web.rest;

import com.br.se.r92.SofttestApp;

import com.br.se.r92.domain.Parecer;
import com.br.se.r92.domain.User;
import com.br.se.r92.domain.Processo;
import com.br.se.r92.repository.ParecerRepository;
import com.br.se.r92.service.ParecerService;
import com.br.se.r92.web.rest.errors.ExceptionTranslator;
import com.br.se.r92.service.dto.ParecerCriteria;
import com.br.se.r92.service.ParecerQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.br.se.r92.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ParecerResource REST controller.
 *
 * @see ParecerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SofttestApp.class)
public class ParecerResourceIntTest {

    private static final String DEFAULT_PARECER = "AAAAAAAAAA";
    private static final String UPDATED_PARECER = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_CADASTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CADASTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_ATUALIZACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ATUALIZACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ParecerRepository parecerRepository;

    @Autowired
    private ParecerService parecerService;

    @Autowired
    private ParecerQueryService parecerQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParecerMockMvc;

    private Parecer parecer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParecerResource parecerResource = new ParecerResource(parecerService, parecerQueryService);
        this.restParecerMockMvc = MockMvcBuilders.standaloneSetup(parecerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    // /**
    //  * Create an entity for this test.
    //  *
    //  * This is a static method, as tests for other entities might also need it,
    //  * if they test an entity which requires the current entity.
    //  */
    // public static Parecer createEntity(EntityManager em) {
    //     Parecer parecer = new Parecer()
    //         .parecer(DEFAULT_PARECER)
    //         .dataCadastro(DEFAULT_DATA_CADASTRO)
    //         .dataAtualizacao(DEFAULT_DATA_ATUALIZACAO);
    //     return parecer;
    // }

    // @Before
    // public void initTest() {
    //     parecer = createEntity(em);
    // }

    // @Test
    // @Transactional
    // public void createParecer() throws Exception {
    //     int databaseSizeBeforeCreate = parecerRepository.findAll().size();

    //     // Create the Parecer
    //     restParecerMockMvc.perform(post("/api/parecers")
    //         .contentType(TestUtil.APPLICATION_JSON_UTF8)
    //         .content(TestUtil.convertObjectToJsonBytes(parecer)))
    //         .andExpect(status().isCreated());

    //     // Validate the Parecer in the database
    //     List<Parecer> parecerList = parecerRepository.findAll();
    //     assertThat(parecerList).hasSize(databaseSizeBeforeCreate + 1);
    //     Parecer testParecer = parecerList.get(parecerList.size() - 1);
    //     assertThat(testParecer.getParecer()).isEqualTo(DEFAULT_PARECER);
    //     assertThat(testParecer.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
    //     assertThat(testParecer.getDataAtualizacao()).isEqualTo(DEFAULT_DATA_ATUALIZACAO);
    // }

    // @Test
    // @Transactional
    // public void createParecerWithExistingId() throws Exception {
    //     int databaseSizeBeforeCreate = parecerRepository.findAll().size();

    //     // Create the Parecer with an existing ID
    //     parecer.setId(1L);

    //     // An entity with an existing ID cannot be created, so this API call must fail
    //     restParecerMockMvc.perform(post("/api/parecers")
    //         .contentType(TestUtil.APPLICATION_JSON_UTF8)
    //         .content(TestUtil.convertObjectToJsonBytes(parecer)))
    //         .andExpect(status().isBadRequest());

    //     // Validate the Parecer in the database
    //     List<Parecer> parecerList = parecerRepository.findAll();
    //     assertThat(parecerList).hasSize(databaseSizeBeforeCreate);
    // }

    // @Test
    // @Transactional
    // public void checkParecerIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = parecerRepository.findAll().size();
    //     // set the field null
    //     parecer.setParecer(null);

    //     // Create the Parecer, which fails.

    //     restParecerMockMvc.perform(post("/api/parecers")
    //         .contentType(TestUtil.APPLICATION_JSON_UTF8)
    //         .content(TestUtil.convertObjectToJsonBytes(parecer)))
    //         .andExpect(status().isBadRequest());

    //     List<Parecer> parecerList = parecerRepository.findAll();
    //     assertThat(parecerList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // public void checkDataCadastroIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = parecerRepository.findAll().size();
    //     // set the field null
    //     parecer.setDataCadastro(null);

    //     // Create the Parecer, which fails.

    //     restParecerMockMvc.perform(post("/api/parecers")
    //         .contentType(TestUtil.APPLICATION_JSON_UTF8)
    //         .content(TestUtil.convertObjectToJsonBytes(parecer)))
    //         .andExpect(status().isBadRequest());

    //     List<Parecer> parecerList = parecerRepository.findAll();
    //     assertThat(parecerList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // public void getAllParecers() throws Exception {
    //     // Initialize the database
    //     parecerRepository.saveAndFlush(parecer);

    //     // Get all the parecerList
    //     restParecerMockMvc.perform(get("/api/parecers?sort=id,desc"))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    //         .andExpect(jsonPath("$.[*].id").value(hasItem(parecer.getId().intValue())))
    //         .andExpect(jsonPath("$.[*].parecer").value(hasItem(DEFAULT_PARECER.toString())))
    //         .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
    //         .andExpect(jsonPath("$.[*].dataAtualizacao").value(hasItem(DEFAULT_DATA_ATUALIZACAO.toString())));
    // }

    // @Test
    // @Transactional
    // public void getParecer() throws Exception {
    //     // Initialize the database
    //     parecerRepository.saveAndFlush(parecer);

    //     // Get the parecer
    //     restParecerMockMvc.perform(get("/api/parecers/{id}", parecer.getId()))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    //         .andExpect(jsonPath("$.id").value(parecer.getId().intValue()))
    //         .andExpect(jsonPath("$.parecer").value(DEFAULT_PARECER.toString()))
    //         .andExpect(jsonPath("$.dataCadastro").value(DEFAULT_DATA_CADASTRO.toString()))
    //         .andExpect(jsonPath("$.dataAtualizacao").value(DEFAULT_DATA_ATUALIZACAO.toString()));
    // }

    // @Test
    // @Transactional
    // public void getAllParecersByParecerIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     parecerRepository.saveAndFlush(parecer);

    //     // Get all the parecerList where parecer equals to DEFAULT_PARECER
    //     defaultParecerShouldBeFound("parecer.equals=" + DEFAULT_PARECER);

    //     // Get all the parecerList where parecer equals to UPDATED_PARECER
    //     defaultParecerShouldNotBeFound("parecer.equals=" + UPDATED_PARECER);
    // }

    // @Test
    // @Transactional
    // public void getAllParecersByParecerIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     parecerRepository.saveAndFlush(parecer);

    //     // Get all the parecerList where parecer in DEFAULT_PARECER or UPDATED_PARECER
    //     defaultParecerShouldBeFound("parecer.in=" + DEFAULT_PARECER + "," + UPDATED_PARECER);

    //     // Get all the parecerList where parecer equals to UPDATED_PARECER
    //     defaultParecerShouldNotBeFound("parecer.in=" + UPDATED_PARECER);
    // }

    // @Test
    // @Transactional
    // public void getAllParecersByParecerIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     parecerRepository.saveAndFlush(parecer);

    //     // Get all the parecerList where parecer is not null
    //     defaultParecerShouldBeFound("parecer.specified=true");

    //     // Get all the parecerList where parecer is null
    //     defaultParecerShouldNotBeFound("parecer.specified=false");
    // }

    // @Test
    // @Transactional
    // public void getAllParecersByDataCadastroIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     parecerRepository.saveAndFlush(parecer);

    //     // Get all the parecerList where dataCadastro equals to DEFAULT_DATA_CADASTRO
    //     defaultParecerShouldBeFound("dataCadastro.equals=" + DEFAULT_DATA_CADASTRO);

    //     // Get all the parecerList where dataCadastro equals to UPDATED_DATA_CADASTRO
    //     defaultParecerShouldNotBeFound("dataCadastro.equals=" + UPDATED_DATA_CADASTRO);
    // }

    // @Test
    // @Transactional
    // public void getAllParecersByDataCadastroIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     parecerRepository.saveAndFlush(parecer);

    //     // Get all the parecerList where dataCadastro in DEFAULT_DATA_CADASTRO or UPDATED_DATA_CADASTRO
    //     defaultParecerShouldBeFound("dataCadastro.in=" + DEFAULT_DATA_CADASTRO + "," + UPDATED_DATA_CADASTRO);

    //     // Get all the parecerList where dataCadastro equals to UPDATED_DATA_CADASTRO
    //     defaultParecerShouldNotBeFound("dataCadastro.in=" + UPDATED_DATA_CADASTRO);
    // }

    // @Test
    // @Transactional
    // public void getAllParecersByDataCadastroIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     parecerRepository.saveAndFlush(parecer);

    //     // Get all the parecerList where dataCadastro is not null
    //     defaultParecerShouldBeFound("dataCadastro.specified=true");

    //     // Get all the parecerList where dataCadastro is null
    //     defaultParecerShouldNotBeFound("dataCadastro.specified=false");
    // }

    // @Test
    // @Transactional
    // public void getAllParecersByDataAtualizacaoIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     parecerRepository.saveAndFlush(parecer);

    //     // Get all the parecerList where dataAtualizacao equals to DEFAULT_DATA_ATUALIZACAO
    //     defaultParecerShouldBeFound("dataAtualizacao.equals=" + DEFAULT_DATA_ATUALIZACAO);

    //     // Get all the parecerList where dataAtualizacao equals to UPDATED_DATA_ATUALIZACAO
    //     defaultParecerShouldNotBeFound("dataAtualizacao.equals=" + UPDATED_DATA_ATUALIZACAO);
    // }

    // @Test
    // @Transactional
    // public void getAllParecersByDataAtualizacaoIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     parecerRepository.saveAndFlush(parecer);

    //     // Get all the parecerList where dataAtualizacao in DEFAULT_DATA_ATUALIZACAO or UPDATED_DATA_ATUALIZACAO
    //     defaultParecerShouldBeFound("dataAtualizacao.in=" + DEFAULT_DATA_ATUALIZACAO + "," + UPDATED_DATA_ATUALIZACAO);

    //     // Get all the parecerList where dataAtualizacao equals to UPDATED_DATA_ATUALIZACAO
    //     defaultParecerShouldNotBeFound("dataAtualizacao.in=" + UPDATED_DATA_ATUALIZACAO);
    // }

    // @Test
    // @Transactional
    // public void getAllParecersByDataAtualizacaoIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     parecerRepository.saveAndFlush(parecer);

    //     // Get all the parecerList where dataAtualizacao is not null
    //     defaultParecerShouldBeFound("dataAtualizacao.specified=true");

    //     // Get all the parecerList where dataAtualizacao is null
    //     defaultParecerShouldNotBeFound("dataAtualizacao.specified=false");
    // }

    // @Test
    // @Transactional
    // public void getAllParecersByUsuarioIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     User usuario = UserResourceIntTest.createEntity(em);
    //     em.persist(usuario);
    //     em.flush();
    //     parecer.setUsuario(usuario);
    //     parecerRepository.saveAndFlush(parecer);
    //     Long usuarioId = usuario.getId();

    //     // Get all the parecerList where usuario equals to usuarioId
    //     defaultParecerShouldBeFound("usuarioId.equals=" + usuarioId);

    //     // Get all the parecerList where usuario equals to usuarioId + 1
    //     defaultParecerShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    // }


    // @Test
    // @Transactional
    // public void getAllParecersByProcessoIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     Processo processo = ProcessoResourceIntTest.createEntity(em);
    //     em.persist(processo);
    //     em.flush();
    //     parecer.setProcesso(processo);
    //     parecerRepository.saveAndFlush(parecer);
    //     Long processoId = processo.getId();

    //     // Get all the parecerList where processo equals to processoId
    //     defaultParecerShouldBeFound("processoId.equals=" + processoId);

    //     // Get all the parecerList where processo equals to processoId + 1
    //     defaultParecerShouldNotBeFound("processoId.equals=" + (processoId + 1));
    // }

    // /**
    //  * Executes the search, and checks that the default entity is returned
    //  */
    // private void defaultParecerShouldBeFound(String filter) throws Exception {
    //     restParecerMockMvc.perform(get("/api/parecers?sort=id,desc&" + filter))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    //         .andExpect(jsonPath("$.[*].id").value(hasItem(parecer.getId().intValue())))
    //         .andExpect(jsonPath("$.[*].parecer").value(hasItem(DEFAULT_PARECER.toString())))
    //         .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
    //         .andExpect(jsonPath("$.[*].dataAtualizacao").value(hasItem(DEFAULT_DATA_ATUALIZACAO.toString())));
    // }

    // /**
    //  * Executes the search, and checks that the default entity is not returned
    //  */
    // private void defaultParecerShouldNotBeFound(String filter) throws Exception {
    //     restParecerMockMvc.perform(get("/api/parecers?sort=id,desc&" + filter))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    //         .andExpect(jsonPath("$").isArray())
    //         .andExpect(jsonPath("$").isEmpty());
    // }


    // @Test
    // @Transactional
    // public void getNonExistingParecer() throws Exception {
    //     // Get the parecer
    //     restParecerMockMvc.perform(get("/api/parecers/{id}", Long.MAX_VALUE))
    //         .andExpect(status().isNotFound());
    // }

    // @Test
    // @Transactional
    // public void updateParecer() throws Exception {
    //     // Initialize the database
    //     parecerService.save(parecer);

    //     int databaseSizeBeforeUpdate = parecerRepository.findAll().size();

    //     // Update the parecer
    //     Parecer updatedParecer = parecerRepository.findOne(parecer.getId());
    //     // Disconnect from session so that the updates on updatedParecer are not directly saved in db
    //     em.detach(updatedParecer);
    //     updatedParecer
    //         .parecer(UPDATED_PARECER)
    //         .dataCadastro(UPDATED_DATA_CADASTRO)
    //         .dataAtualizacao(UPDATED_DATA_ATUALIZACAO);

    //     restParecerMockMvc.perform(put("/api/parecers")
    //         .contentType(TestUtil.APPLICATION_JSON_UTF8)
    //         .content(TestUtil.convertObjectToJsonBytes(updatedParecer)))
    //         .andExpect(status().isOk());

    //     // Validate the Parecer in the database
    //     List<Parecer> parecerList = parecerRepository.findAll();
    //     assertThat(parecerList).hasSize(databaseSizeBeforeUpdate);
    //     Parecer testParecer = parecerList.get(parecerList.size() - 1);
    //     assertThat(testParecer.getParecer()).isEqualTo(UPDATED_PARECER);
    //     assertThat(testParecer.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
    //     assertThat(testParecer.getDataAtualizacao()).isEqualTo(UPDATED_DATA_ATUALIZACAO);
    // }

    // @Test
    // @Transactional
    // public void updateNonExistingParecer() throws Exception {
    //     int databaseSizeBeforeUpdate = parecerRepository.findAll().size();

    //     // Create the Parecer

    //     // If the entity doesn't have an ID, it will be created instead of just being updated
    //     restParecerMockMvc.perform(put("/api/parecers")
    //         .contentType(TestUtil.APPLICATION_JSON_UTF8)
    //         .content(TestUtil.convertObjectToJsonBytes(parecer)))
    //         .andExpect(status().isCreated());

    //     // Validate the Parecer in the database
    //     List<Parecer> parecerList = parecerRepository.findAll();
    //     assertThat(parecerList).hasSize(databaseSizeBeforeUpdate + 1);
    // }

    // @Test
    // @Transactional
    // public void deleteParecer() throws Exception {
    //     // Initialize the database
    //     parecerService.save(parecer);

    //     int databaseSizeBeforeDelete = parecerRepository.findAll().size();

    //     // Get the parecer
    //     restParecerMockMvc.perform(delete("/api/parecers/{id}", parecer.getId())
    //         .accept(TestUtil.APPLICATION_JSON_UTF8))
    //         .andExpect(status().isOk());

    //     // Validate the database is empty
    //     List<Parecer> parecerList = parecerRepository.findAll();
    //     assertThat(parecerList).hasSize(databaseSizeBeforeDelete - 1);
    // }

    // @Test
    // @Transactional
    // public void equalsVerifier() throws Exception {
    //     TestUtil.equalsVerifier(Parecer.class);
    //     Parecer parecer1 = new Parecer();
    //     parecer1.setId(1L);
    //     Parecer parecer2 = new Parecer();
    //     parecer2.setId(parecer1.getId());
    //     assertThat(parecer1).isEqualTo(parecer2);
    //     parecer2.setId(2L);
    //     assertThat(parecer1).isNotEqualTo(parecer2);
    //     parecer1.setId(null);
    //     assertThat(parecer1).isNotEqualTo(parecer2);
    // }
}
