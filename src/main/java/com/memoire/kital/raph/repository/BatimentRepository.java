package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Batiment;

import com.memoire.kital.raph.service.dto.BatimentCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface BatimentRepository extends JpaRepository<Batiment, String>, JpaSpecificationExecutor<Batiment> {

/*    @Query(value = "SELECT b FROM Batiment b WHERE " +
        "b.nom LIKE CONCAT('%', :query ,'%')")
    Page<Batiment> searchBatimentList(String query, Pageable pageable);*/
}
