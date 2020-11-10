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
@Mapper(uses = {PassengerMapper.class, TicketScheduleSectionMapper.class})
public interface TicketMapper {

    /**
     * To dto ticket dto.
     *
     * @param ticketEntity the ticket entity
     * @return the ticket dto
     */
    @Mappings({
            @Mapping(target = "passengerDto", source = "ticketEntity.passengerEntity"),
            @Mapping(target = "ticketScheduleSectionDtoList", source = "ticketEntity.ticketScheduleSectionEntityList")
    })
    TicketDto toDto(TicketEntity ticketEntity);

    /**
     * To dto list list.
     *
     * @param ticketEntityList the ticket entity list
     * @return the list
     */
    List<TicketDto> toDtoList(List<TicketEntity> ticketEntityList);

    /**
     * To entity ticket entity.
     *
     * @param ticketDto the ticket dto
     * @return the ticket entity
     */
    @Mappings({

            @Mapping(target = "passengerEntity", source = "ticketDto.passengerDto"),
            @Mapping(target = "ticketScheduleSectionEntityList", source = "ticketDto.ticketScheduleSectionDtoList")
    })
    TicketEntity toEntity(TicketDto ticketDto);

    /**
     * To entity list list.
     *
     * @param ticketDtoList the ticket dto list
     * @return the list
     */
    List<TicketEntity> toEntityList(List<TicketDto> ticketDtoList);

}
