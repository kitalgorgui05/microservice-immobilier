package com.memoire.kital.raph.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Batiment} entity.
 */
public class BatimentDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 3, max = 10)
    private String nom;

    @NotNull
    private Integer nombreSalle;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getNombreSalle() {
        return nombreSalle;
    }

    public void setNombreSalle(Integer nombreSalle) {
        this.nombreSalle = nombreSalle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatimentDTO)) {
            return false;
        }

        return id != null && id.equals(((BatimentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BatimentDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nombreSalle=" + getNombreSalle() +
            "}";
    }
}
