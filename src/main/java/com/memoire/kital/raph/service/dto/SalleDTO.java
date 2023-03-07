package com.memoire.kital.raph.service.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SalleDTO implements Serializable {
    private String id;
    private String nom;
    private Integer nombre;
    private BatimentDTO batiment;

    public SalleDTO(String id) {
        this.id = id;
    }
}
