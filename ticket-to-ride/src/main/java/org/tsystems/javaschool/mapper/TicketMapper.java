package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.TicketDto;
import org.tsystems.javaschool.model.entity.TicketEntity;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mappings({
            @Mapping(target = "passengerDto", source = "ticketEntity.passengerEntity"),
            @Mapping(target = "sectionDtoList", source = "ticketEntity.sectionEntitySet")
    })
    TicketDto toDto(TicketEntity ticketEntity);

}
