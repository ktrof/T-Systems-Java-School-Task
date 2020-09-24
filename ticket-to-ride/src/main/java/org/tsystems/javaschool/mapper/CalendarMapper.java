package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.CalendarDto;
import org.tsystems.javaschool.model.entity.CalendarEntity;

@Mapper(componentModel = "spring")
public interface CalendarMapper {

    @Mappings({
            @Mapping(target = "trainDto", source = "calendarEntity.trainEntity")
    })
    CalendarDto toDto(CalendarEntity calendarEntity);

    @Mappings({
            @Mapping(target = "trainEntity", source = "calendarDto.trainDto")
    })
    CalendarEntity toEntity(CalendarDto calendarDto);

}
