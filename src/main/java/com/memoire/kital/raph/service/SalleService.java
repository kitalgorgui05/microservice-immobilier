package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.SalleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SalleService {
    SalleDTO save(SalleDTO salleDTO);
    Page<SalleDTO> findAll(Pageable pageable);
    Optional<SalleDTO> findOne(String id);
    void delete(String id);
}
