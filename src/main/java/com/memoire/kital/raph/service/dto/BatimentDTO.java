package com.memoire.kital.raph.service.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class BatimentDTO implements Serializable {
    private String id;
    private String nom;
    private Integer nombreSalle;

    public BatimentDTO(String id) {
        this.id = id;
    }
    public BatimentDTO(String id, String nom, Integer nombreSalle) {
        this.id = id;
        this.nom = nom;
        this.nombreSalle = nombreSalle;
    }
}
