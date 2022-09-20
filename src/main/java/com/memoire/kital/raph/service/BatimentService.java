package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.BatimentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.Batiment}.
 */
public interface BatimentService {

    /**
     * Save a batiment.
     *
     * @param batimentDTO the entity to save.
     * @return the persisted entity.
     */
    BatimentDTO save(BatimentDTO batimentDTO);

    /**
     * Get all the batiments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BatimentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" batiment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BatimentDTO> findOne(Long id);

    /**
     * Delete the "id" batiment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
