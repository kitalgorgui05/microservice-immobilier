package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.SalleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Salle} and its DTO {@link SalleDTO}.
 */
@Mapper(componentModel = "spring", uses = {BatimentMapper.class})
public interface SalleMapper extends EntityMapper<SalleDTO, Salle> {

    @Mapping(source = "batiment.id", target = "batimentId")
    SalleDTO toDto(Salle salle);

    @Mapping(source = "batimentId", target = "batiment")
    Salle toEntity(SalleDTO salleDTO);

    default Salle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Salle salle = new Salle();
        salle.setId(id);
        return salle;
    }
}
