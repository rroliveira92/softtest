package com.br.se.r92.web.rest;

import com.br.se.r92.SofttestApp;

import com.br.se.r92.domain.Processo;
import com.br.se.r92.domain.User;
import com.br.se.r92.domain.User;
import com.br.se.r92.domain.User;
import com.br.se.r92.repository.ProcessoRepository;
import com.br.se.r92.service.ProcessoService;
import com.br.se.r92.web.rest.errors.ExceptionTranslator;
import com.br.se.r92.service.dto.ProcessoCriteria;
import com.br.se.r92.service.ProcessoQueryService;

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
 * Test class for the ProcessoResource REST controller.
 *
 * @see ProcessoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SofttestApp.class)
public class ProcessoResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_CADASTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CADASTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_ATUALIZACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ATUALIZACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private ProcessoQueryService processoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProcessoMockMvc;

    private Processo processo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcessoResource processoResource = new ProcessoResource(processoService, processoQueryService);
        this.restProcessoMockMvc = MockMvcBuilders.standaloneSetup(processoResource)
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
    // public static Processo createEntity(EntityManager em) {
    //     Processo processo = new Processo()
    //         .descricao(DEFAULT_DESCRICAO)
    //         .dataCadastro(DEFAULT_DATA_CADASTRO)
    //         .dataAtualizacao(DEFAULT_DATA_ATUALIZACAO);
    //     return processo;
    // }

    // @Before
    // public void initTest() {
    //     processo = createEntity(em);
    // }

    // @Test
    // @Transactional
    // public void createProcesso() throws Exception {
    //     int databaseSizeBeforeCreate = processoRepository.findAll().size();

    //     // Create the Processo
    //     restProcessoMockMvc.perform(post("/api/processos")
    //         .contentType(TestUtil.APPLICATION_JSON_UTF8)
    //         .content(TestUtil.convertObjectToJsonBytes(processo)))
    //         .andExpect(status().isCreated());

    //     // Validate the Processo in the database
    //     List<Processo> processoList = processoRepository.findAll();
    //     assertThat(processoList).hasSize(databaseSizeBeforeCreate + 1);
    //     Processo testProcesso = processoList.get(processoList.size() - 1);
    //     assertThat(testProcesso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    //     assertThat(testProcesso.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
    //     assertThat(testProcesso.getDataAtualizacao()).isEqualTo(DEFAULT_DATA_ATUALIZACAO);
    // }

    // @Test
    // @Transactional
    // public void createProcessoWithExistingId() throws Exception {
    //     int databaseSizeBeforeCreate = processoRepository.findAll().size();

    //     // Create the Processo with an existing ID
    //     processo.setId(1L);

    //     // An entity with an existing ID cannot be created, so this API call must fail
    //     restProcessoMockMvc.perform(post("/api/processos")
    //         .contentType(TestUtil.APPLICATION_JSON_UTF8)
    //         .content(TestUtil.convertObjectToJsonBytes(processo)))
    //         .andExpect(status().isBadRequest());

    //     // Validate the Processo in the database
    //     List<Processo> processoList = processoRepository.findAll();
    //     assertThat(processoList).hasSize(databaseSizeBeforeCreate);
    // }

    // @Test
    // @Transactional
    // public void checkDescricaoIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = processoRepository.findAll().size();
    //     // set the field null
    //     processo.setDescricao(null);

    //     // Create the Processo, which fails.

    //     restProcessoMockMvc.perform(post("/api/processos")
    //         .contentType(TestUtil.APPLICATION_JSON_UTF8)
    //         .content(TestUtil.convertObjectToJsonBytes(processo)))
    //         .andExpect(status().isBadRequest());

    //     List<Processo> processoList = processoRepository.findAll();
    //     assertThat(processoList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // public void checkDataCadastroIsRequired() throws Exception {
    //     int databaseSizeBeforeTest = processoRepository.findAll().size();
    //     // set the field null
    //     processo.setDataCadastro(null);

    //     // Create the Processo, which fails.

    //     restProcessoMockMvc.perform(post("/api/processos")
    //         .contentType(TestUtil.APPLICATION_JSON_UTF8)
    //         .content(TestUtil.convertObjectToJsonBytes(processo)))
    //         .andExpect(status().isBadRequest());

    //     List<Processo> processoList = processoRepository.findAll();
    //     assertThat(processoList).hasSize(databaseSizeBeforeTest);
    // }

    // @Test
    // @Transactional
    // public void getAllProcessos() throws Exception {
    //     // Initialize the database
    //     processoRepository.saveAndFlush(processo);

    //     // Get all the processoList
    //     restProcessoMockMvc.perform(get("/api/processos?sort=id,desc"))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    //         .andExpect(jsonPath("$.[*].id").value(hasItem(processo.getId().intValue())))
    //         .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
    //         .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
    //         .andExpect(jsonPath("$.[*].dataAtualizacao").value(hasItem(DEFAULT_DATA_ATUALIZACAO.toString())));
    // }

    // @Test
    // @Transactional
    // public void getProcesso() throws Exception {
    //     // Initialize the database
    //     processoRepository.saveAndFlush(processo);

    //     // Get the processo
    //     restProcessoMockMvc.perform(get("/api/processos/{id}", processo.getId()))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    //         .andExpect(jsonPath("$.id").value(processo.getId().intValue()))
    //         .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
    //         .andExpect(jsonPath("$.dataCadastro").value(DEFAULT_DATA_CADASTRO.toString()))
    //         .andExpect(jsonPath("$.dataAtualizacao").value(DEFAULT_DATA_ATUALIZACAO.toString()));
    // }

    // @Test
    // @Transactional
    // public void getAllProcessosByDescricaoIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     processoRepository.saveAndFlush(processo);

    //     // Get all the processoList where descricao equals to DEFAULT_DESCRICAO
    //     defaultProcessoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

    //     // Get all the processoList where descricao equals to UPDATED_DESCRICAO
    //     defaultProcessoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    // }

    // @Test
    // @Transactional
    // public void getAllProcessosByDescricaoIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     processoRepository.saveAndFlush(processo);

    //     // Get all the processoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
    //     defaultProcessoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

    //     // Get all the processoList where descricao equals to UPDATED_DESCRICAO
    //     defaultProcessoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    // }

    // @Test
    // @Transactional
    // public void getAllProcessosByDescricaoIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     processoRepository.saveAndFlush(processo);

    //     // Get all the processoList where descricao is not null
    //     defaultProcessoShouldBeFound("descricao.specified=true");

    //     // Get all the processoList where descricao is null
    //     defaultProcessoShouldNotBeFound("descricao.specified=false");
    // }

    // @Test
    // @Transactional
    // public void getAllProcessosByDataCadastroIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     processoRepository.saveAndFlush(processo);

    //     // Get all the processoList where dataCadastro equals to DEFAULT_DATA_CADASTRO
    //     defaultProcessoShouldBeFound("dataCadastro.equals=" + DEFAULT_DATA_CADASTRO);

    //     // Get all the processoList where dataCadastro equals to UPDATED_DATA_CADASTRO
    //     defaultProcessoShouldNotBeFound("dataCadastro.equals=" + UPDATED_DATA_CADASTRO);
    // }

    // @Test
    // @Transactional
    // public void getAllProcessosByDataCadastroIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     processoRepository.saveAndFlush(processo);

    //     // Get all the processoList where dataCadastro in DEFAULT_DATA_CADASTRO or UPDATED_DATA_CADASTRO
    //     defaultProcessoShouldBeFound("dataCadastro.in=" + DEFAULT_DATA_CADASTRO + "," + UPDATED_DATA_CADASTRO);

    //     // Get all the processoList where dataCadastro equals to UPDATED_DATA_CADASTRO
    //     defaultProcessoShouldNotBeFound("dataCadastro.in=" + UPDATED_DATA_CADASTRO);
    // }

    // @Test
    // @Transactional
    // public void getAllProcessosByDataCadastroIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     processoRepository.saveAndFlush(processo);

    //     // Get all the processoList where dataCadastro is not null
    //     defaultProcessoShouldBeFound("dataCadastro.specified=true");

    //     // Get all the processoList where dataCadastro is null
    //     defaultProcessoShouldNotBeFound("dataCadastro.specified=false");
    // }

    // @Test
    // @Transactional
    // public void getAllProcessosByDataAtualizacaoIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     processoRepository.saveAndFlush(processo);

    //     // Get all the processoList where dataAtualizacao equals to DEFAULT_DATA_ATUALIZACAO
    //     defaultProcessoShouldBeFound("dataAtualizacao.equals=" + DEFAULT_DATA_ATUALIZACAO);

    //     // Get all the processoList where dataAtualizacao equals to UPDATED_DATA_ATUALIZACAO
    //     defaultProcessoShouldNotBeFound("dataAtualizacao.equals=" + UPDATED_DATA_ATUALIZACAO);
    // }

    // @Test
    // @Transactional
    // public void getAllProcessosByDataAtualizacaoIsInShouldWork() throws Exception {
    //     // Initialize the database
    //     processoRepository.saveAndFlush(processo);

    //     // Get all the processoList where dataAtualizacao in DEFAULT_DATA_ATUALIZACAO or UPDATED_DATA_ATUALIZACAO
    //     defaultProcessoShouldBeFound("dataAtualizacao.in=" + DEFAULT_DATA_ATUALIZACAO + "," + UPDATED_DATA_ATUALIZACAO);

    //     // Get all the processoList where dataAtualizacao equals to UPDATED_DATA_ATUALIZACAO
    //     defaultProcessoShouldNotBeFound("dataAtualizacao.in=" + UPDATED_DATA_ATUALIZACAO);
    // }

    // @Test
    // @Transactional
    // public void getAllProcessosByDataAtualizacaoIsNullOrNotNull() throws Exception {
    //     // Initialize the database
    //     processoRepository.saveAndFlush(processo);

    //     // Get all the processoList where dataAtualizacao is not null
    //     defaultProcessoShouldBeFound("dataAtualizacao.specified=true");

    //     // Get all the processoList where dataAtualizacao is null
    //     defaultProcessoShouldNotBeFound("dataAtualizacao.specified=false");
    // }

    // @Test
    // @Transactional
    // public void getAllProcessosByUsuarioCadastroIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     User usuarioCadastro = UserResourceIntTest.createEntity(em);
    //     em.persist(usuarioCadastro);
    //     em.flush();
    //     processo.setUsuarioCadastro(usuarioCadastro);
    //     processoRepository.saveAndFlush(processo);
    //     Long usuarioCadastroId = usuarioCadastro.getId();

    //     // Get all the processoList where usuarioCadastro equals to usuarioCadastroId
    //     defaultProcessoShouldBeFound("usuarioCadastroId.equals=" + usuarioCadastroId);

    //     // Get all the processoList where usuarioCadastro equals to usuarioCadastroId + 1
    //     defaultProcessoShouldNotBeFound("usuarioCadastroId.equals=" + (usuarioCadastroId + 1));
    // }


    // @Test
    // @Transactional
    // public void getAllProcessosByUsuarioAtualizacaoIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     User usuarioAtualizacao = UserResourceIntTest.createEntity(em);
    //     em.persist(usuarioAtualizacao);
    //     em.flush();
    //     processo.setUsuarioAtualizacao(usuarioAtualizacao);
    //     processoRepository.saveAndFlush(processo);
    //     Long usuarioAtualizacaoId = usuarioAtualizacao.getId();

    //     // Get all the processoList where usuarioAtualizacao equals to usuarioAtualizacaoId
    //     defaultProcessoShouldBeFound("usuarioAtualizacaoId.equals=" + usuarioAtualizacaoId);

    //     // Get all the processoList where usuarioAtualizacao equals to usuarioAtualizacaoId + 1
    //     defaultProcessoShouldNotBeFound("usuarioAtualizacaoId.equals=" + (usuarioAtualizacaoId + 1));
    // }


    // @Test
    // @Transactional
    // public void getAllProcessosByUsuariosParecerIsEqualToSomething() throws Exception {
    //     // Initialize the database
    //     User usuariosParecer = UserResourceIntTest.createEntity(em);
    //     em.persist(usuariosParecer);
    //     em.flush();
    //     processo.addUsuariosParecer(usuariosParecer);
    //     processoRepository.saveAndFlush(processo);
    //     Long usuariosParecerId = usuariosParecer.getId();

    //     // Get all the processoList where usuariosParecer equals to usuariosParecerId
    //     defaultProcessoShouldBeFound("usuariosParecerId.equals=" + usuariosParecerId);

    //     // Get all the processoList where usuariosParecer equals to usuariosParecerId + 1
    //     defaultProcessoShouldNotBeFound("usuariosParecerId.equals=" + (usuariosParecerId + 1));
    // }

    // /**
    //  * Executes the search, and checks that the default entity is returned
    //  */
    // private void defaultProcessoShouldBeFound(String filter) throws Exception {
    //     restProcessoMockMvc.perform(get("/api/processos?sort=id,desc&" + filter))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    //         .andExpect(jsonPath("$.[*].id").value(hasItem(processo.getId().intValue())))
    //         .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
    //         .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
    //         .andExpect(jsonPath("$.[*].dataAtualizacao").value(hasItem(DEFAULT_DATA_ATUALIZACAO.toString())));
    // }

    // /**
    //  * Executes the search, and checks that the default entity is not returned
    //  */
    // private void defaultProcessoShouldNotBeFound(String filter) throws Exception {
    //     restProcessoMockMvc.perform(get("/api/processos?sort=id,desc&" + filter))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    //         .andExpect(jsonPath("$").isArray())
    //         .andExpect(jsonPath("$").isEmpty());
    // }


    // @Test
    // @Transactional
    // public void getNonExistingProcesso() throws Exception {
    //     // Get the processo
    //     restProcessoMockMvc.perform(get("/api/processos/{id}", Long.MAX_VALUE))
    //         .andExpect(status().isNotFound());
    // }

    // @Test
    // @Transactional
    // public void updateProcesso() throws Exception {
    //     // Initialize the database
    //     processoService.save(processo);

    //     int databaseSizeBeforeUpdate = processoRepository.findAll().size();

    //     // Update the processo
    //     Processo updatedProcesso = processoRepository.findOne(processo.getId());
    //     // Disconnect from session so that the updates on updatedProcesso are not directly saved in db
    //     em.detach(updatedProcesso);
    //     updatedProcesso
    //         .descricao(UPDATED_DESCRICAO)
    //         .dataCadastro(UPDATED_DATA_CADASTRO)
    //         .dataAtualizacao(UPDATED_DATA_ATUALIZACAO);

    //     restProcessoMockMvc.perform(put("/api/processos")
    //         .contentType(TestUtil.APPLICATION_JSON_UTF8)
    //         .content(TestUtil.convertObjectToJsonBytes(updatedProcesso)))
    //         .andExpect(status().isOk());

    //     // Validate the Processo in the database
    //     List<Processo> processoList = processoRepository.findAll();
    //     assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    //     Processo testProcesso = processoList.get(processoList.size() - 1);
    //     assertThat(testProcesso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    //     assertThat(testProcesso.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
    //     assertThat(testProcesso.getDataAtualizacao()).isEqualTo(UPDATED_DATA_ATUALIZACAO);
    // }

    // @Test
    // @Transactional
    // public void updateNonExistingProcesso() throws Exception {
    //     int databaseSizeBeforeUpdate = processoRepository.findAll().size();

    //     // Create the Processo

    //     // If the entity doesn't have an ID, it will be created instead of just being updated
    //     restProcessoMockMvc.perform(put("/api/processos")
    //         .contentType(TestUtil.APPLICATION_JSON_UTF8)
    //         .content(TestUtil.convertObjectToJsonBytes(processo)))
    //         .andExpect(status().isCreated());

    //     // Validate the Processo in the database
    //     List<Processo> processoList = processoRepository.findAll();
    //     assertThat(processoList).hasSize(databaseSizeBeforeUpdate + 1);
    // }

    // @Test
    // @Transactional
    // public void deleteProcesso() throws Exception {
    //     // Initialize the database
    //     processoService.save(processo);

    //     int databaseSizeBeforeDelete = processoRepository.findAll().size();

    //     // Get the processo
    //     restProcessoMockMvc.perform(delete("/api/processos/{id}", processo.getId())
    //         .accept(TestUtil.APPLICATION_JSON_UTF8))
    //         .andExpect(status().isOk());

    //     // Validate the database is empty
    //     List<Processo> processoList = processoRepository.findAll();
    //     assertThat(processoList).hasSize(databaseSizeBeforeDelete - 1);
    // }

    // @Test
    // @Transactional
    // public void equalsVerifier() throws Exception {
    //     TestUtil.equalsVerifier(Processo.class);
    //     Processo processo1 = new Processo();
    //     processo1.setId(1L);
    //     Processo processo2 = new Processo();
    //     processo2.setId(processo1.getId());
    //     assertThat(processo1).isEqualTo(processo2);
    //     processo2.setId(2L);
    //     assertThat(processo1).isNotEqualTo(processo2);
    //     processo1.setId(null);
    //     assertThat(processo1).isNotEqualTo(processo2);
    // }
}
