package com.memoire.kital.raph.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

public class SalleCriteria implements Serializable, Criteria {

    private StringFilter id;

    private StringFilter nom;

    private IntegerFilter nombre;

    private StringFilter batiment;

    public SalleCriteria() {
    }

    public SalleCriteria(SalleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.batiment = other.batiment == null ? null : other.batiment.copy();
    }

    @Override
    public SalleCriteria copy() {
        return new SalleCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public IntegerFilter getNombre() {
        return nombre;
    }

    public void setNombre(IntegerFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getBatimentId() {
        return batiment;
    }

    public void setBatimentId(StringFilter batimentId) {
        this.batiment = batiment;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SalleCriteria that = (SalleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(batiment, that.batiment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        nombre,
        batiment
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (batiment != null ? "batiment=" + batiment + ", " : "") +
            "}";
    }

}
