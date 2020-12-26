package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ConcertDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Concert} and its DTO {@link ConcertDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConcertMapper extends EntityMapper<ConcertDTO, Concert> {


    @Mapping(target = "bands", ignore = true)
    @Mapping(target = "removeBand", ignore = true)
    @Mapping(target = "customers", ignore = true)
    @Mapping(target = "removeCustomer", ignore = true)
    Concert toEntity(ConcertDTO concertDTO);

    default Concert fromId(Long id) {
        if (id == null) {
            return null;
        }
        Concert concert = new Concert();
        concert.setId(id);
        return concert;
    }
}
