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

/**
 * Criteria class for the {@link com.memoire.kital.raph.domain.Salle} entity. This class is used
 * in {@link com.memoire.kital.raph.web.rest.SalleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /salles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SalleCriteria implements Serializable, Criteria {

    private StringFilter id;

    private StringFilter nom;

    private IntegerFilter nombre;

    private StringFilter batimentId;

    public SalleCriteria() {
    }

    public SalleCriteria(SalleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.batimentId = other.batimentId == null ? null : other.batimentId.copy();
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
        return batimentId;
    }

    public void setBatimentId(StringFilter batimentId) {
        this.batimentId = batimentId;
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
            Objects.equals(batimentId, that.batimentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        nombre,
        batimentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (batimentId != null ? "batimentId=" + batimentId + ", " : "") +
            "}";
    }

}
