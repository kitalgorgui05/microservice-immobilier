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

import com.memoire.kital.raph.domain.Batiment;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.BatimentRepository;
import com.memoire.kital.raph.service.dto.BatimentCriteria;
import com.memoire.kital.raph.service.dto.BatimentDTO;
import com.memoire.kital.raph.service.mapper.BatimentMapper;

@Service
@Transactional(readOnly = true)
public class BatimentQueryService extends QueryService<Batiment> {
    private final Logger log = LoggerFactory.getLogger(BatimentQueryService.class);
    private final BatimentRepository batimentRepository;
    private final BatimentMapper batimentMapper;

    public BatimentQueryService(BatimentRepository batimentRepository, BatimentMapper batimentMapper) {
        this.batimentRepository = batimentRepository;
        this.batimentMapper = batimentMapper;
    }
    @Transactional(readOnly = true)
    public List<BatimentDTO> findByCriteria(BatimentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Batiment> specification = createSpecification(criteria);
        return batimentMapper.toDto(batimentRepository.findAll(specification));
    }
    @Transactional(readOnly = true)
    public Page<BatimentDTO> findByCriteria(BatimentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Batiment> specification = createSpecification(criteria);
        return batimentRepository.findAll(specification, page)
            .map(batimentMapper::toDto);
    }
    @Transactional(readOnly = true)
    public long countByCriteria(BatimentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Batiment> specification = createSpecification(criteria);
        return batimentRepository.count(specification);
    }

    protected Specification<Batiment> createSpecification(BatimentCriteria criteria) {
        Specification<Batiment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), Batiment_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Batiment_.nom));
            }
            if (criteria.getNombreSalle() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNombreSalle(), Batiment_.nombreSalle));
            }
        }
        return specification;
    }
}
