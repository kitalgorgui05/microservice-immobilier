package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.ImmoblierApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Salle;
import com.memoire.kital.raph.domain.Batiment;
import com.memoire.kital.raph.repository.SalleRepository;
import com.memoire.kital.raph.service.SalleService;
import com.memoire.kital.raph.service.dto.SalleDTO;
import com.memoire.kital.raph.service.mapper.SalleMapper;
import com.memoire.kital.raph.service.dto.SalleCriteria;
import com.memoire.kital.raph.service.SalleQueryService;

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
 * Integration tests for the {@link SalleResource} REST controller.
 */
@SpringBootTest(classes = { ImmoblierApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SalleResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBRE = 1;
    private static final Integer UPDATED_NOMBRE = 2;
    private static final Integer SMALLER_NOMBRE = 1 - 1;

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private SalleMapper salleMapper;

    @Autowired
    private SalleService salleService;

    @Autowired
    private SalleQueryService salleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalleMockMvc;

    private Salle salle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Salle createEntity(EntityManager em) {
        Salle salle = new Salle()
            .nom(DEFAULT_NOM)
            .nombre(DEFAULT_NOMBRE);
        return salle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Salle createUpdatedEntity(EntityManager em) {
        Salle salle = new Salle()
            .nom(UPDATED_NOM)
            .nombre(UPDATED_NOMBRE);
        return salle;
    }

    @BeforeEach
    public void initTest() {
        salle = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalle() throws Exception {
        int databaseSizeBeforeCreate = salleRepository.findAll().size();
        // Create the Salle
        SalleDTO salleDTO = salleMapper.toDto(salle);
        restSalleMockMvc.perform(post("/api/salles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isCreated());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeCreate + 1);
        Salle testSalle = salleList.get(salleList.size() - 1);
        assertThat(testSalle.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSalle.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createSalleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salleRepository.findAll().size();

        // Create the Salle with an existing ID
        salle.setId(null);
        SalleDTO salleDTO = salleMapper.toDto(salle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalleMockMvc.perform(post("/api/salles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = salleRepository.findAll().size();
        // set the field null
        salle.setNom(null);

        // Create the Salle, which fails.
        SalleDTO salleDTO = salleMapper.toDto(salle);


        restSalleMockMvc.perform(post("/api/salles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isBadRequest());

        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = salleRepository.findAll().size();
        // set the field null
        salle.setNombre(null);

        // Create the Salle, which fails.
        SalleDTO salleDTO = salleMapper.toDto(salle);


        restSalleMockMvc.perform(post("/api/salles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isBadRequest());

        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalles() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList
        restSalleMockMvc.perform(get("/api/salles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salle.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    public void getSalle() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get the salle
        restSalleMockMvc.perform(get("/api/salles/{id}", salle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salle.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }


    @Test
    @Transactional
    public void getSallesByIdFiltering() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        String id = salle.getId();

        defaultSalleShouldBeFound("id.equals=" + id);
        defaultSalleShouldNotBeFound("id.notEquals=" + id);

        defaultSalleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSalleShouldNotBeFound("id.greaterThan=" + id);

        defaultSalleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSalleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSallesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList where nom equals to DEFAULT_NOM
        defaultSalleShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the salleList where nom equals to UPDATED_NOM
        defaultSalleShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllSallesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList where nom not equals to DEFAULT_NOM
        defaultSalleShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the salleList where nom not equals to UPDATED_NOM
        defaultSalleShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllSallesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultSalleShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the salleList where nom equals to UPDATED_NOM
        defaultSalleShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllSallesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList where nom is not null
        defaultSalleShouldBeFound("nom.specified=true");

        // Get all the salleList where nom is null
        defaultSalleShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllSallesByNomContainsSomething() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList where nom contains DEFAULT_NOM
        defaultSalleShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the salleList where nom contains UPDATED_NOM
        defaultSalleShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllSallesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList where nom does not contain DEFAULT_NOM
        defaultSalleShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the salleList where nom does not contain UPDATED_NOM
        defaultSalleShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllSallesByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList where nombre equals to DEFAULT_NOMBRE
        defaultSalleShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the salleList where nombre equals to UPDATED_NOMBRE
        defaultSalleShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllSallesByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList where nombre not equals to DEFAULT_NOMBRE
        defaultSalleShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the salleList where nombre not equals to UPDATED_NOMBRE
        defaultSalleShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllSallesByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultSalleShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the salleList where nombre equals to UPDATED_NOMBRE
        defaultSalleShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllSallesByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList where nombre is not null
        defaultSalleShouldBeFound("nombre.specified=true");

        // Get all the salleList where nombre is null
        defaultSalleShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllSallesByNombreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList where nombre is greater than or equal to DEFAULT_NOMBRE
        defaultSalleShouldBeFound("nombre.greaterThanOrEqual=" + DEFAULT_NOMBRE);

        // Get all the salleList where nombre is greater than or equal to UPDATED_NOMBRE
        defaultSalleShouldNotBeFound("nombre.greaterThanOrEqual=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllSallesByNombreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList where nombre is less than or equal to DEFAULT_NOMBRE
        defaultSalleShouldBeFound("nombre.lessThanOrEqual=" + DEFAULT_NOMBRE);

        // Get all the salleList where nombre is less than or equal to SMALLER_NOMBRE
        defaultSalleShouldNotBeFound("nombre.lessThanOrEqual=" + SMALLER_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllSallesByNombreIsLessThanSomething() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList where nombre is less than DEFAULT_NOMBRE
        defaultSalleShouldNotBeFound("nombre.lessThan=" + DEFAULT_NOMBRE);

        // Get all the salleList where nombre is less than UPDATED_NOMBRE
        defaultSalleShouldBeFound("nombre.lessThan=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllSallesByNombreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList where nombre is greater than DEFAULT_NOMBRE
        defaultSalleShouldNotBeFound("nombre.greaterThan=" + DEFAULT_NOMBRE);

        // Get all the salleList where nombre is greater than SMALLER_NOMBRE
        defaultSalleShouldBeFound("nombre.greaterThan=" + SMALLER_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllSallesByBatimentIsEqualToSomething() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);
        Batiment batiment = BatimentResourceIT.createEntity(em);
        em.persist(batiment);
        em.flush();
        salle.setBatiment(batiment);
        salleRepository.saveAndFlush(salle);
        String batimentId = batiment.getId();

        // Get all the salleList where batiment equals to batimentId
        defaultSalleShouldBeFound("batimentId.equals=" + batimentId);

        // Get all the salleList where batiment equals to batimentId + 1
        defaultSalleShouldNotBeFound("batimentId.equals=" + (batimentId + 1));
    }

    private void defaultSalleShouldBeFound(String filter) throws Exception {
        restSalleMockMvc.perform(get("/api/salles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salle.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));

        // Check, that the count call also returns 1
        restSalleMockMvc.perform(get("/api/salles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSalleShouldNotBeFound(String filter) throws Exception {
        restSalleMockMvc.perform(get("/api/salles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSalleMockMvc.perform(get("/api/salles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSalle() throws Exception {
        // Get the salle
        restSalleMockMvc.perform(get("/api/salles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalle() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        int databaseSizeBeforeUpdate = salleRepository.findAll().size();

        // Update the salle
        Salle updatedSalle = salleRepository.findById(salle.getId()).get();
        // Disconnect from session so that the updates on updatedSalle are not directly saved in db
        em.detach(updatedSalle);
        updatedSalle
            .nom(UPDATED_NOM)
            .nombre(UPDATED_NOMBRE);
        SalleDTO salleDTO = salleMapper.toDto(updatedSalle);

        restSalleMockMvc.perform(put("/api/salles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isOk());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeUpdate);
        Salle testSalle = salleList.get(salleList.size() - 1);
        assertThat(testSalle.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSalle.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingSalle() throws Exception {
        int databaseSizeBeforeUpdate = salleRepository.findAll().size();

        // Create the Salle
        SalleDTO salleDTO = salleMapper.toDto(salle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalleMockMvc.perform(put("/api/salles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSalle() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        int databaseSizeBeforeDelete = salleRepository.findAll().size();

        // Delete the salle
        restSalleMockMvc.perform(delete("/api/salles/{id}", salle.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
