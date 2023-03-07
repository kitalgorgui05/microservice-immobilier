package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Batiment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface BatimentRepository extends JpaRepository<Batiment, String>, JpaSpecificationExecutor<Batiment> {
}
