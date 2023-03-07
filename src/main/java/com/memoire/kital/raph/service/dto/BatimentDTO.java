package com.memoire.kital.raph.service.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BatimentDTO implements Serializable {
    private String id;
    private String nom;
    private Integer nombreSalle;

    public BatimentDTO(String id) {
        this.id = id;
    }
}
