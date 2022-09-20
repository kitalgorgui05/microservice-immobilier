package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Salle;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Salle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalleRepository extends JpaRepository<Salle, Long>, JpaSpecificationExecutor<Salle> {
}
