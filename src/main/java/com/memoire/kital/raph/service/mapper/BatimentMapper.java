package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.BatimentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Batiment} and its DTO {@link BatimentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BatimentMapper extends EntityMapper<BatimentDTO, Batiment> {



    default Batiment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Batiment batiment = new Batiment();
        batiment.setId(id);
        return batiment;
    }
}