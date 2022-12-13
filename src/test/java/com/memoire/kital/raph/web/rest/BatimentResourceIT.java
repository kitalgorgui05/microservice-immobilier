package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.ImmoblierApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Batiment;
import com.memoire.kital.raph.repository.BatimentRepository;
import com.memoire.kital.raph.service.BatimentService;
import com.memoire.kital.raph.service.dto.BatimentDTO;
import com.memoire.kital.raph.service.mapper.BatimentMapper;
import com.memoire.kital.raph.service.dto.BatimentCriteria;
import com.memoire.kital.raph.service.BatimentQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BatimentResource} REST controller.
 */
@SpringBootTest(classes = { ImmoblierApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class BatimentResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBRE_SALLE = 1;
    private static final Integer UPDATED_NOMBRE_SALLE = 2;
    private static final Integer SMALLER_NOMBRE_SALLE = 1 - 1;

    @Autowired
    private BatimentRepository batimentRepository;

    @Autowired
    private BatimentMapper batimentMapper;

    @Autowired
    private BatimentService batimentService;

    @Autowired
    private BatimentQueryService batimentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBatimentMockMvc;

    private Batiment batiment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Batiment createEntity(EntityManager em) {
        Batiment batiment = new Batiment()
            .nom(DEFAULT_NOM)
            .nombreSalle(DEFAULT_NOMBRE_SALLE);
        return batiment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Batiment createUpdatedEntity(EntityManager em) {
        Batiment batiment = new Batiment()
            .nom(UPDATED_NOM)
            .nombreSalle(UPDATED_NOMBRE_SALLE);
        return batiment;
    }

    @BeforeEach
    public void initTest() {
        batiment = createEntity(em);
    }

    @Test
    @Transactional
    public void createBatiment() throws Exception {
        int databaseSizeBeforeCreate = batimentRepository.findAll().size();
        // Create the Batiment
        BatimentDTO batimentDTO = batimentMapper.toDto(batiment);
        restBatimentMockMvc.perform(post("/api/batiments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(batimentDTO)))
            .andExpect(status().isCreated());

        // Validate the Batiment in the database
        List<Batiment> batimentList = batimentRepository.findAll();
        assertThat(batimentList).hasSize(databaseSizeBeforeCreate + 1);
        Batiment testBatiment = batimentList.get(batimentList.size() - 1);
        assertThat(testBatiment.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testBatiment.getNombreSalle()).isEqualTo(DEFAULT_NOMBRE_SALLE);
    }

    @Test
    @Transactional
    public void createBatimentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = batimentRepository.findAll().size();

        // Create the Batiment with an existing ID
        batiment.setId(null);
        BatimentDTO batimentDTO = batimentMapper.toDto(batiment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBatimentMockMvc.perform(post("/api/batiments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(batimentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Batiment in the database
        List<Batiment> batimentList = batimentRepository.findAll();
        assertThat(batimentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = batimentRepository.findAll().size();
        // set the field null
        batiment.setNom(null);

        // Create the Batiment, which fails.
        BatimentDTO batimentDTO = batimentMapper.toDto(batiment);


        restBatimentMockMvc.perform(post("/api/batiments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(batimentDTO)))
            .andExpect(status().isBadRequest());

        List<Batiment> batimentList = batimentRepository.findAll();
        assertThat(batimentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreSalleIsRequired() throws Exception {
        int databaseSizeBeforeTest = batimentRepository.findAll().size();
        // set the field null
        batiment.setNombreSalle(null);

        // Create the Batiment, which fails.
        BatimentDTO batimentDTO = batimentMapper.toDto(batiment);


        restBatimentMockMvc.perform(post("/api/batiments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(batimentDTO)))
            .andExpect(status().isBadRequest());

        List<Batiment> batimentList = batimentRepository.findAll();
        assertThat(batimentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBatiments() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get all the batimentList
        restBatimentMockMvc.perform(get("/api/batiments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(batiment.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nombreSalle").value(hasItem(DEFAULT_NOMBRE_SALLE)));
    }

    @Test
    @Transactional
    public void getBatiment() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get the batiment
        restBatimentMockMvc.perform(get("/api/batiments/{id}", batiment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(batiment.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nombreSalle").value(DEFAULT_NOMBRE_SALLE));
    }


    @Test
    @Transactional
    public void getBatimentsByIdFiltering() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        String id = batiment.getId();

        defaultBatimentShouldBeFound("id.equals=" + id);
        defaultBatimentShouldNotBeFound("id.notEquals=" + id);

        defaultBatimentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBatimentShouldNotBeFound("id.greaterThan=" + id);

        defaultBatimentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBatimentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBatimentsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get all the batimentList where nom equals to DEFAULT_NOM
        defaultBatimentShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the batimentList where nom equals to UPDATED_NOM
        defaultBatimentShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllBatimentsByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get all the batimentList where nom not equals to DEFAULT_NOM
        defaultBatimentShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the batimentList where nom not equals to UPDATED_NOM
        defaultBatimentShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllBatimentsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get all the batimentList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultBatimentShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the batimentList where nom equals to UPDATED_NOM
        defaultBatimentShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllBatimentsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get all the batimentList where nom is not null
        defaultBatimentShouldBeFound("nom.specified=true");

        // Get all the batimentList where nom is null
        defaultBatimentShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllBatimentsByNomContainsSomething() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get all the batimentList where nom contains DEFAULT_NOM
        defaultBatimentShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the batimentList where nom contains UPDATED_NOM
        defaultBatimentShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllBatimentsByNomNotContainsSomething() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get all the batimentList where nom does not contain DEFAULT_NOM
        defaultBatimentShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the batimentList where nom does not contain UPDATED_NOM
        defaultBatimentShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllBatimentsByNombreSalleIsEqualToSomething() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get all the batimentList where nombreSalle equals to DEFAULT_NOMBRE_SALLE
        defaultBatimentShouldBeFound("nombreSalle.equals=" + DEFAULT_NOMBRE_SALLE);

        // Get all the batimentList where nombreSalle equals to UPDATED_NOMBRE_SALLE
        defaultBatimentShouldNotBeFound("nombreSalle.equals=" + UPDATED_NOMBRE_SALLE);
    }

    @Test
    @Transactional
    public void getAllBatimentsByNombreSalleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get all the batimentList where nombreSalle not equals to DEFAULT_NOMBRE_SALLE
        defaultBatimentShouldNotBeFound("nombreSalle.notEquals=" + DEFAULT_NOMBRE_SALLE);

        // Get all the batimentList where nombreSalle not equals to UPDATED_NOMBRE_SALLE
        defaultBatimentShouldBeFound("nombreSalle.notEquals=" + UPDATED_NOMBRE_SALLE);
    }

    @Test
    @Transactional
    public void getAllBatimentsByNombreSalleIsInShouldWork() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get all the batimentList where nombreSalle in DEFAULT_NOMBRE_SALLE or UPDATED_NOMBRE_SALLE
        defaultBatimentShouldBeFound("nombreSalle.in=" + DEFAULT_NOMBRE_SALLE + "," + UPDATED_NOMBRE_SALLE);

        // Get all the batimentList where nombreSalle equals to UPDATED_NOMBRE_SALLE
        defaultBatimentShouldNotBeFound("nombreSalle.in=" + UPDATED_NOMBRE_SALLE);
    }

    @Test
    @Transactional
    public void getAllBatimentsByNombreSalleIsNullOrNotNull() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get all the batimentList where nombreSalle is not null
        defaultBatimentShouldBeFound("nombreSalle.specified=true");

        // Get all the batimentList where nombreSalle is null
        defaultBatimentShouldNotBeFound("nombreSalle.specified=false");
    }

    @Test
    @Transactional
    public void getAllBatimentsByNombreSalleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get all the batimentList where nombreSalle is greater than or equal to DEFAULT_NOMBRE_SALLE
        defaultBatimentShouldBeFound("nombreSalle.greaterThanOrEqual=" + DEFAULT_NOMBRE_SALLE);

        // Get all the batimentList where nombreSalle is greater than or equal to UPDATED_NOMBRE_SALLE
        defaultBatimentShouldNotBeFound("nombreSalle.greaterThanOrEqual=" + UPDATED_NOMBRE_SALLE);
    }

    @Test
    @Transactional
    public void getAllBatimentsByNombreSalleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get all the batimentList where nombreSalle is less than or equal to DEFAULT_NOMBRE_SALLE
        defaultBatimentShouldBeFound("nombreSalle.lessThanOrEqual=" + DEFAULT_NOMBRE_SALLE);

        // Get all the batimentList where nombreSalle is less than or equal to SMALLER_NOMBRE_SALLE
        defaultBatimentShouldNotBeFound("nombreSalle.lessThanOrEqual=" + SMALLER_NOMBRE_SALLE);
    }

    @Test
    @Transactional
    public void getAllBatimentsByNombreSalleIsLessThanSomething() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get all the batimentList where nombreSalle is less than DEFAULT_NOMBRE_SALLE
        defaultBatimentShouldNotBeFound("nombreSalle.lessThan=" + DEFAULT_NOMBRE_SALLE);

        // Get all the batimentList where nombreSalle is less than UPDATED_NOMBRE_SALLE
        defaultBatimentShouldBeFound("nombreSalle.lessThan=" + UPDATED_NOMBRE_SALLE);
    }

    @Test
    @Transactional
    public void getAllBatimentsByNombreSalleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        // Get all the batimentList where nombreSalle is greater than DEFAULT_NOMBRE_SALLE
        defaultBatimentShouldNotBeFound("nombreSalle.greaterThan=" + DEFAULT_NOMBRE_SALLE);

        // Get all the batimentList where nombreSalle is greater than SMALLER_NOMBRE_SALLE
        defaultBatimentShouldBeFound("nombreSalle.greaterThan=" + SMALLER_NOMBRE_SALLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBatimentShouldBeFound(String filter) throws Exception {
        restBatimentMockMvc.perform(get("/api/batiments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(batiment.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nombreSalle").value(hasItem(DEFAULT_NOMBRE_SALLE)));

        // Check, that the count call also returns 1
        restBatimentMockMvc.perform(get("/api/batiments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBatimentShouldNotBeFound(String filter) throws Exception {
        restBatimentMockMvc.perform(get("/api/batiments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBatimentMockMvc.perform(get("/api/batiments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBatiment() throws Exception {
        // Get the batiment
        restBatimentMockMvc.perform(get("/api/batiments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBatiment() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        int databaseSizeBeforeUpdate = batimentRepository.findAll().size();

        // Update the batiment
        Batiment updatedBatiment = batimentRepository.findById(batiment.getId()).get();
        // Disconnect from session so that the updates on updatedBatiment are not directly saved in db
        em.detach(updatedBatiment);
        updatedBatiment
            .nom(UPDATED_NOM)
            .nombreSalle(UPDATED_NOMBRE_SALLE);
        BatimentDTO batimentDTO = batimentMapper.toDto(updatedBatiment);

        restBatimentMockMvc.perform(put("/api/batiments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(batimentDTO)))
            .andExpect(status().isOk());

        // Validate the Batiment in the database
        List<Batiment> batimentList = batimentRepository.findAll();
        assertThat(batimentList).hasSize(databaseSizeBeforeUpdate);
        Batiment testBatiment = batimentList.get(batimentList.size() - 1);
        assertThat(testBatiment.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testBatiment.getNombreSalle()).isEqualTo(UPDATED_NOMBRE_SALLE);
    }

    @Test
    @Transactional
    public void updateNonExistingBatiment() throws Exception {
        int databaseSizeBeforeUpdate = batimentRepository.findAll().size();

        // Create the Batiment
        BatimentDTO batimentDTO = batimentMapper.toDto(batiment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatimentMockMvc.perform(put("/api/batiments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(batimentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Batiment in the database
        List<Batiment> batimentList = batimentRepository.findAll();
        assertThat(batimentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBatiment() throws Exception {
        // Initialize the database
        batimentRepository.saveAndFlush(batiment);

        int databaseSizeBeforeDelete = batimentRepository.findAll().size();

        // Delete the batiment
        restBatimentMockMvc.perform(delete("/api/batiments/{id}", batiment.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Batiment> batimentList = batimentRepository.findAll();
        assertThat(batimentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
