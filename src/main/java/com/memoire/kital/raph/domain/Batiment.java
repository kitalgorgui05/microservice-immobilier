package com.memoire.kital.raph.domain;

import com.memoire.kital.raph.utils.SizeMapper;
import lombok.*;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "batiments")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Batiment implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id",unique = true)
    private String id;

    @NotNull
    @Size(min = SizeMapper.SizeMapperBatiment.MIN, max = SizeMapper.SizeMapperBatiment.MAX)
    @Column(name = "nom", length = SizeMapper.SizeMapperBatiment.MAX, nullable = false, unique = true)
    private String nom;

    @NotNull
    @Column(name = "nombre_salle", nullable = false)
    private Integer nombreSalle;
}
