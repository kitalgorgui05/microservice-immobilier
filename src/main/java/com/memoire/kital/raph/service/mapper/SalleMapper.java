package com.memoire.kital.raph.service.mapper;

import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.SalleDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {BatimentMapper.class})
public interface SalleMapper extends EntityMapper<SalleDTO, Salle> {

    default Salle fromId(String id) {
        if (id == null) {
            return null;
        }
        Salle salle = new Salle();
        salle.setId(id);
        return salle;
    }
}
