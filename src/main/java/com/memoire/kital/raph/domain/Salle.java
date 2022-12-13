package com.memoire.kital.raph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Salle.
 */
@Entity
@Table(name = "salles")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Salle implements Serializable {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id",unique = true)
    private String id;

    @NotNull
    @Column(name = "nom", nullable = false, unique = true)
    private String nom;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private Integer nombre;

    @ManyToOne
    @JsonIgnoreProperties(value = "salles", allowSetters = true)
    private Batiment batiment;

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

    public Salle nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getNombre() {
        return nombre;
    }

    public Salle nombre(Integer nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(Integer nombre) {
        this.nombre = nombre;
    }

    public Batiment getBatiment() {
        return batiment;
    }

    public Salle batiment(Batiment batiment) {
        this.batiment = batiment;
        return this;
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Salle)) {
            return false;
        }
        return id != null && id.equals(((Salle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Salle{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nombre=" + getNombre() +
            "}";
    }
}
