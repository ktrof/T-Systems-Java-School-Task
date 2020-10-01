package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.TicketDto;
import org.tsystems.javaschool.model.entity.TicketEntity;

import java.util.List;

/**
 * The interface Ticket mapper.
 *
 * @author Trofim Kremen
 */
@Mapper
public interface TicketMapper {

    /**
     * To dto ticket dto.
     *
     * @param ticketEntity the ticket entity
     * @return the ticket dto
     */
    @Mappings({
            @Mapping(target = "passengerDto", source = "ticketEntity.passengerEntity"),
            @Mapping(target = "scheduleSectionDtoList", source = "ticketEntity.scheduleSectionEntitySet")
    })
    TicketDto toDto(TicketEntity ticketEntity);

    /**
     * To dto list list.
     *
     * @param ticketEntityList the ticket entity list
     * @return the list
     */
    List<TicketDto> toDtoList(List<TicketEntity> ticketEntityList);

}
