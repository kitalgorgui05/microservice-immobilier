package com.memoire.kital.raph.domain;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Batiment.
 */
@Entity
@Table(name = "batiments")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Batiment implements Serializable {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id",unique = true)
    private String id;

    @NotNull
    @Size(min = 3, max = 10)
    @Column(name = "nom", length = 10, nullable = false, unique = true)
    private String nom;

    @NotNull
    @Column(name = "nombre_salle", nullable = false)
    private Integer nombreSalle;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Batiment nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getNombreSalle() {
        return nombreSalle;
    }

    public Batiment nombreSalle(Integer nombreSalle) {
        this.nombreSalle = nombreSalle;
        return this;
    }

    public void setNombreSalle(Integer nombreSalle) {
        this.nombreSalle = nombreSalle;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Batiment)) {
            return false;
        }
        return id != null && id.equals(((Batiment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Batiment{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nombreSalle=" + getNombreSalle() +
            "}";
    }
}
