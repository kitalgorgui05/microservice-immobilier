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
@AllArgsConstructor
@EqualsAndHashCode
public class SalleDTO implements Serializable {
    private String id;
    private String nom;
    private Integer nombre;
    private BatimentDTO batiment;
}
