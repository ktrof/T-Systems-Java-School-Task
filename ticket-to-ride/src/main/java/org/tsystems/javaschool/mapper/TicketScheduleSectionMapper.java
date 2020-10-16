package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.TicketScheduleSectionDto;
import org.tsystems.javaschool.model.entity.TicketScheduleSectionEntity;

import java.util.List;

/**
 * The interface Ticket schedule section mapper.
 *
 * @author Trofim Kremen
 */
@Mapper(uses = {ScheduleSectionMapper.class, TicketMapper.class})
public interface TicketScheduleSectionMapper {

    /**
     * To dto ticket schedule section dto.
     *
     * @param ticketScheduleSectionEntity the ticket schedule section entity
     * @return the ticket schedule section dto
     */
    @Mappings({
            @Mapping(target = "ticketId", source = "ticketScheduleSectionEntity.ticketEntity.id"),
            @Mapping(target = "scheduleSectionId", source = "ticketScheduleSectionEntity.scheduleSectionEntity.id")
    })
    TicketScheduleSectionDto toDto(TicketScheduleSectionEntity ticketScheduleSectionEntity);

    /**
     * To dto list.
     *
     * @param ticketScheduleSectionEntityList the ticket schedule section entity list
     * @return the list
     */
    List<TicketScheduleSectionDto> toDto(List<TicketScheduleSectionEntity> ticketScheduleSectionEntityList);

}
