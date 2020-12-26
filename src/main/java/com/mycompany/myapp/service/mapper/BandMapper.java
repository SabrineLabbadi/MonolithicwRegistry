package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.BandDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Band} and its DTO {@link BandDTO}.
 */
@Mapper(componentModel = "spring", uses = {ConcertMapper.class})
public interface BandMapper extends EntityMapper<BandDTO, Band> {

    @Mapping(source = "concert.id", target = "concertId")
    @Mapping(source = "concert.name", target = "concertName")
    BandDTO toDto(Band band);

    @Mapping(source = "concertId", target = "concert")
    Band toEntity(BandDTO bandDTO);

    default Band fromId(Long id) {
        if (id == null) {
            return null;
        }
        Band band = new Band();
        band.setId(id);
        return band;
    }
}
