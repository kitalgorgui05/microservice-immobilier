package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.BatimentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BatimentService {
    BatimentDTO save(BatimentDTO batimentDTO);
    Page<BatimentDTO> findAll(Pageable pageable);
    Optional<BatimentDTO> findOne(String id);
    //Page<BatimentDTO> rechercheBatiment(String query,Pageable pageable);
    void delete(String id);
}
