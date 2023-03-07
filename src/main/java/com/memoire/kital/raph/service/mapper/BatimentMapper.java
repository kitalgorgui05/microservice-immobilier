package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.BatimentDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {})
public interface BatimentMapper extends EntityMapper<BatimentDTO, Batiment> {

    default Batiment fromId(String id) {
        if (id == null) {
            return null;
        }
        Batiment batiment = new Batiment();
        batiment.setId(id);
        return batiment;
    }
}
