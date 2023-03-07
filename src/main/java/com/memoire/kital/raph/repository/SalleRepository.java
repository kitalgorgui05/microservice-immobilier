package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Salle;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface SalleRepository extends JpaRepository<Salle, String>, JpaSpecificationExecutor<Salle> {
}
