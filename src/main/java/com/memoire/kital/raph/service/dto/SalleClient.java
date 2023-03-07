package com.memoire.kital.raph.service.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SalleClient {
    private String id;
    private String nom;
    private BatimentDTO batiment;
}
