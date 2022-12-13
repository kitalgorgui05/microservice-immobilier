package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.SalleService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.SalleDTO;
import com.memoire.kital.raph.service.dto.SalleCriteria;
import com.memoire.kital.raph.service.SalleQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.memoire.kital.raph.domain.Salle}.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class SalleResource {

    private final Logger log = LoggerFactory.getLogger(SalleResource.class);

    private static final String ENTITY_NAME = "immoblierSalle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalleService salleService;

    private final SalleQueryService salleQueryService;

    public SalleResource(SalleService salleService, SalleQueryService salleQueryService) {
        this.salleService = salleService;
        this.salleQueryService = salleQueryService;
    }

    /**
     * {@code POST  /salles} : Create a new salle.
     *
     * @param salleDTO the salleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salleDTO, or with status {@code 400 (Bad Request)} if the salle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/salles")
    public ResponseEntity<SalleDTO> createSalle(@Valid @RequestBody SalleDTO salleDTO) throws URISyntaxException {
        log.debug("REST request to save Salle : {}", salleDTO);
        if (salleDTO.getId() != null) {
            throw new BadRequestAlertException("A new salle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalleDTO result = salleService.save(salleDTO);
        return ResponseEntity.created(new URI("/api/salles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /salles} : Updates an existing salle.
     *
     * @param salleDTO the salleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salleDTO,
     * or with status {@code 400 (Bad Request)} if the salleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/salles")
    public ResponseEntity<SalleDTO> updateSalle(@Valid @RequestBody SalleDTO salleDTO) throws URISyntaxException {
        log.debug("REST request to update Salle : {}", salleDTO);
        if (salleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalleDTO result = salleService.save(salleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /salles} : get all the salles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salles in body.
     */
    @GetMapping("/salles")
    public ResponseEntity<List<SalleDTO>> getAllSalles(SalleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Salles by criteria: {}", criteria);
        Page<SalleDTO> page = salleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /salles/count} : count all the salles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/salles/count")
    public ResponseEntity<Long> countSalles(SalleCriteria criteria) {
        log.debug("REST request to count Salles by criteria: {}", criteria);
        return ResponseEntity.ok().body(salleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /salles/:id} : get the "id" salle.
     *
     * @param id the id of the salleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/salles/{id}")
    public ResponseEntity<SalleDTO> getSalle(@PathVariable String id) {
        log.debug("REST request to get Salle : {}", id);
        Optional<SalleDTO> salleDTO = salleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salleDTO);
    }

    /**
     * {@code DELETE  /salles/:id} : delete the "id" salle.
     *
     * @param id the id of the salleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/salles/{id}")
    public ResponseEntity<Void> deleteSalle(@PathVariable String id) {
        log.debug("REST request to delete Salle : {}", id);
        salleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
