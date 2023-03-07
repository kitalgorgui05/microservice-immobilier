package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.BatimentService;
import com.memoire.kital.raph.domain.Batiment;
import com.memoire.kital.raph.repository.BatimentRepository;
import com.memoire.kital.raph.service.dto.BatimentDTO;
import com.memoire.kital.raph.service.mapper.BatimentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
public class BatimentServiceImpl implements BatimentService {
    private final Logger log = LoggerFactory.getLogger(BatimentServiceImpl.class);
    private final BatimentRepository batimentRepository;
    private final BatimentMapper batimentMapper;

    public BatimentServiceImpl(BatimentRepository batimentRepository, BatimentMapper batimentMapper) {
        this.batimentRepository = batimentRepository;
        this.batimentMapper = batimentMapper;
    }
    @Override
    public BatimentDTO save(BatimentDTO batimentDTO) {
        log.debug("Request to save Batiment : {}", batimentDTO);
        Batiment batiment = batimentMapper.toEntity(batimentDTO);
        batiment = batimentRepository.saveAndFlush(batiment);
        return batimentMapper.toDto(batiment);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<BatimentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Batiments");
        return batimentRepository.findAll(pageable)
            .map(batimentMapper::toDto);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<BatimentDTO> findOne(String id) {
        log.debug("Request to get Batiment : {}", id);
        return batimentRepository.findById(id)
            .map(batimentMapper::toDto);
    }
    @Override
    public void delete(String id) {
        log.debug("Request to delete Batiment : {}", id);
        batimentRepository.deleteById(id);
    }
}
