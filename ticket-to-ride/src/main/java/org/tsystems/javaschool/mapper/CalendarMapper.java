package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.CalendarDto;
import org.tsystems.javaschool.model.entity.CalendarEntity;

import java.util.List;

/**
 * The interface Calendar mapper.
 *
 * @author Trofim Kremen
 */
@Mapper
public interface CalendarMapper {

    /**
     * To dto calendar dto.
     *
     * @param calendarEntity the calendar entity
     * @return the calendar dto
     */
    @Mappings({
            @Mapping(target = "trainDto", source = "calendarEntity.trainEntity"),
    })
    CalendarDto toDto(CalendarEntity calendarEntity);

    /**
     * To entity calendar entity.
     *
     * @param calendarDto the calendar dto
     * @return the calendar entity
     */
    @Mappings({
            @Mapping(target = "trainEntity", source = "calendarDto.trainDto")
    })
    CalendarEntity toEntity(CalendarDto calendarDto);

    /**
     * To dto list list.
     *
     * @param calendarEntityList the calendar entity list
     * @return the list
     */
    List<CalendarDto> toDtoList(List<CalendarEntity> calendarEntityList);

    /**
     * To entity list list.
     *
     * @param calendarDtoList the calendar dto list
     * @return the list
     */
    List<CalendarEntity> toEntityList(List<CalendarDto> calendarDtoList);


}
