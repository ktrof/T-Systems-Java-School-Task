package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
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
    CalendarDto toDto(CalendarEntity calendarEntity);

    /**
     * To dto list.
     *
     * @param calendarEntityList the calendar entity list
     * @return the list
     */
    List<CalendarDto> toDto(List<CalendarEntity> calendarEntityList);

}
