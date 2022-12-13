package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.security.AuthoritiesConstants;
import com.memoire.kital.raph.service.BatimentService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.BatimentDTO;
import com.memoire.kital.raph.service.dto.BatimentCriteria;
import com.memoire.kital.raph.service.BatimentQueryService;

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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.memoire.kital.raph.domain.Batiment}.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BatimentResource {

    private final Logger log = LoggerFactory.getLogger(BatimentResource.class);

    private static final String ENTITY_NAME = "immoblierBatiment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BatimentService batimentService;

    private final BatimentQueryService batimentQueryService;

    public BatimentResource(BatimentService batimentService, BatimentQueryService batimentQueryService) {
        this.batimentService = batimentService;
        this.batimentQueryService = batimentQueryService;
    }

    /**
     * {@code POST  /batiments} : Create a new batiment.
     *
     * @param batimentDTO the batimentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new batimentDTO, or with status {@code 400 (Bad Request)} if the batiment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured(
        {AuthoritiesConstants.ADMIN
        }
    )
    @PostMapping("/batiments")
    public ResponseEntity<BatimentDTO> createBatiment(@Valid @RequestBody BatimentDTO batimentDTO) throws URISyntaxException {
        log.debug("REST request to save Batiment : {}", batimentDTO);
        if (batimentDTO.getId() != null) {
            throw new BadRequestAlertException("A new batiment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BatimentDTO result = batimentService.save(batimentDTO);
        return ResponseEntity.created(new URI("/api/batiments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /batiments} : Updates an existing batiment.
     *
     * @param batimentDTO the batimentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batimentDTO,
     * or with status {@code 400 (Bad Request)} if the batimentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the batimentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured(
        {
            AuthoritiesConstants.ADMIN
        }
    )
    @PutMapping("/batiments")
    public ResponseEntity<BatimentDTO> updateBatiment(@Valid @RequestBody BatimentDTO batimentDTO) throws URISyntaxException {
        log.debug("REST request to update Batiment : {}", batimentDTO);
        if (batimentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BatimentDTO result = batimentService.save(batimentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, batimentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /batiments} : get all the batiments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of batiments in body.
     */
    @GetMapping("/batiments")
    public ResponseEntity<List<BatimentDTO>> getAllBatiments(BatimentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Batiments by criteria: {}", criteria);
        Page<BatimentDTO> page = batimentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /batiments/count} : count all the batiments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */

    // pour compter nombre de Batiments
    @GetMapping("/batiments/count")
    public ResponseEntity<Long> countBatiments(BatimentCriteria criteria) {
        log.debug("REST request to count Batiments by criteria: {}", criteria);
        return ResponseEntity.ok().body(batimentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /batiments/:id} : get the "id" batiment.
     *
     * @param id the id of the batimentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the batimentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/batiments/{id}")
    public ResponseEntity<BatimentDTO> getBatiment(@PathVariable String id) {
        log.debug("REST request to get Batiment : {}", id);
        Optional<BatimentDTO> batimentDTO = batimentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(batimentDTO);
    }

    /**
     * {@code DELETE  /batiments/:id} : delete the "id" batiment.
     *
     * @param id the id of the batimentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Secured(
        {
            AuthoritiesConstants.ADMIN
        }
    )
    @DeleteMapping("/batiments/{id}")
    public ResponseEntity<Void> deleteBatiment(@PathVariable String id) {
        log.debug("REST request to delete Batiment : {}", id);
        batimentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
