package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.ScheduleDto;
import org.tsystems.javaschool.model.entity.ScheduleEntity;

@Mapper(componentModel = "spring", uses = {TrainMapper.class})
public interface ScheduleMapper {

    @Mappings({
            @Mapping(target = "id", source = "scheduleEntity.id"),
            @Mapping(target = "sectionDto", source = "scheduleEntity.sectionEntity"),
            @Mapping(target = "trainDto", source = "trainEntity"),
            @Mapping(target = "arrival", source = "scheduleEntity.arrival"),
            @Mapping(target = "departure", source = "scheduleEntity.departure")
    })
    ScheduleDto toDto(ScheduleEntity scheduleEntity);

    @Mappings({
            @Mapping(target = "id", source = "scheduleDto.id"),
            @Mapping(target = "sectionEntity", source = "scheduleDto.sectionDto"),
            @Mapping(target = "trainEntity", source = "scheduleDto.trainDto"),
            @Mapping(target = "arrival", source = "scheduleDto.arrival"),
            @Mapping(target = "departure", source = "scheduleDto.departure")
    })
    ScheduleEntity toEntity(ScheduleDto scheduleDto);

}
