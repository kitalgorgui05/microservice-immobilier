package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.SalleService;
import com.memoire.kital.raph.domain.Salle;
import com.memoire.kital.raph.repository.SalleRepository;
import com.memoire.kital.raph.service.dto.SalleDTO;
import com.memoire.kital.raph.service.mapper.SalleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SalleServiceImpl implements SalleService {

    private final Logger log = LoggerFactory.getLogger(SalleServiceImpl.class);

    private final SalleRepository salleRepository;

    private final SalleMapper salleMapper;

    public SalleServiceImpl(SalleRepository salleRepository, SalleMapper salleMapper) {
        this.salleRepository = salleRepository;
        this.salleMapper = salleMapper;
    }

    @Override
    public SalleDTO save(SalleDTO salleDTO) {
        log.debug("Request to save Salle : {}", salleDTO);
        Salle salle = salleMapper.toEntity(salleDTO);
        salle = salleRepository.saveAndFlush(salle);
        return salleMapper.toDto(salle);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Salles");
        return salleRepository.findAll(pageable)
            .map(salleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SalleDTO> findOne(String id) {
        log.debug("Request to get Salle : {}", id);
        return salleRepository.findById(id)
            .map(salleMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Salle : {}", id);
        salleRepository.deleteById(id);
    }
}
