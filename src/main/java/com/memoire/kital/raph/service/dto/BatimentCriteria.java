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
 * Criteria class for the {@link com.memoire.kital.raph.domain.Batiment} entity. This class is used
 * in {@link com.memoire.kital.raph.web.rest.BatimentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /batiments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BatimentCriteria implements Serializable, Criteria {

    private StringFilter id;

    private StringFilter nom;

    private IntegerFilter nombreSalle;

    public BatimentCriteria() {
    }

    public BatimentCriteria(BatimentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.nombreSalle = other.nombreSalle == null ? null : other.nombreSalle.copy();
    }

    @Override
    public BatimentCriteria copy() {
        return new BatimentCriteria(this);
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

    public IntegerFilter getNombreSalle() {
        return nombreSalle;
    }

    public void setNombreSalle(IntegerFilter nombreSalle) {
        this.nombreSalle = nombreSalle;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BatimentCriteria that = (BatimentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(nombreSalle, that.nombreSalle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        nombreSalle
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BatimentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (nombreSalle != null ? "nombreSalle=" + nombreSalle + ", " : "") +
            "}";
    }

}
