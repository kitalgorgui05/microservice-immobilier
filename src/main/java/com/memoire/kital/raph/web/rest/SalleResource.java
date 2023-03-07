package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.SalleService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.SalleDTO;
import com.memoire.kital.raph.service.dto.SalleCriteria;
import com.memoire.kital.raph.service.SalleQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
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

    @GetMapping("/salles")
    public ResponseEntity<List<SalleDTO>> getAllSalles(SalleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Salles by criteria: {}", criteria);
        Page<SalleDTO> page = salleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/salles/count")
    public ResponseEntity<Long> countSalles(SalleCriteria criteria) {
        log.debug("REST request to count Salles by criteria: {}", criteria);
        return ResponseEntity.ok().body(salleQueryService.countByCriteria(criteria));
    }

    @GetMapping("/salles/{id}")
    public Optional<SalleDTO> getSalle(@PathVariable(name = "id") String id) {
        log.debug("REST request to get Salle : {}", id);
        Optional<SalleDTO> salleDTO = salleService.findOne(id);
        return salleDTO;
    }

    @DeleteMapping("/salles/delete/{id}")
    public ResponseEntity<Void> deleteSalle(@PathVariable String id) {
        log.debug("REST request to delete Salle : {}", id);
        salleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
