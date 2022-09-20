package com.memoire.kital.raph.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.memoire.kital.raph.domain.Salle;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.SalleRepository;
import com.memoire.kital.raph.service.dto.SalleCriteria;
import com.memoire.kital.raph.service.dto.SalleDTO;
import com.memoire.kital.raph.service.mapper.SalleMapper;

/**
 * Service for executing complex queries for {@link Salle} entities in the database.
 * The main input is a {@link SalleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SalleDTO} or a {@link Page} of {@link SalleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalleQueryService extends QueryService<Salle> {

    private final Logger log = LoggerFactory.getLogger(SalleQueryService.class);

    private final SalleRepository salleRepository;

    private final SalleMapper salleMapper;

    public SalleQueryService(SalleRepository salleRepository, SalleMapper salleMapper) {
        this.salleRepository = salleRepository;
        this.salleMapper = salleMapper;
    }

    /**
     * Return a {@link List} of {@link SalleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SalleDTO> findByCriteria(SalleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Salle> specification = createSpecification(criteria);
        return salleMapper.toDto(salleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SalleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SalleDTO> findByCriteria(SalleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Salle> specification = createSpecification(criteria);
        return salleRepository.findAll(specification, page)
            .map(salleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Salle> specification = createSpecification(criteria);
        return salleRepository.count(specification);
    }

    /**
     * Function to convert {@link SalleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Salle> createSpecification(SalleCriteria criteria) {
        Specification<Salle> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Salle_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Salle_.nom));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNombre(), Salle_.nombre));
            }
            if (criteria.getBatimentId() != null) {
                specification = specification.and(buildSpecification(criteria.getBatimentId(),
                    root -> root.join(Salle_.batiment, JoinType.LEFT).get(Batiment_.id)));
            }
        }
        return specification;
    }
}
