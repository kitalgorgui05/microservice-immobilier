package com.memoire.kital.raph.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Salle} entity.
 */
public class SalleDTO implements Serializable {

    private String id;

    @NotNull
    private String nom;

    @NotNull
    private Integer nombre;
    //private String batimentId;

    private BatimentDTO batiment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getNombre() {
        return nombre;
    }

    public void setNombre(Integer nombre) {
        this.nombre = nombre;
    }

    public BatimentDTO getBatiment() {
        return batiment;
    }

    public void setBatiment(BatimentDTO batiment) {
        this.batiment = batiment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalleDTO)) {
            return false;
        }

        return id != null && id.equals(((SalleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalleDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nombre=" + getNombre() +
            ", batimentId=" + getBatiment()+
            "}";
    }
}
