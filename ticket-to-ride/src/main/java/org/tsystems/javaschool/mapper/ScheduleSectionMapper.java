package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.ScheduleSectionDto;
import org.tsystems.javaschool.model.entity.ScheduleSectionEntity;

import java.time.Instant;
import java.util.List;

/**
 * The interface Schedule section mapper.
 *
 * @author Trofim Kremen
 */
@Mapper(uses = {TrainMapper.class, SectionMapper.class})
public interface ScheduleSectionMapper {

    /**
     * To dto schedule dto.
     *
     * @param scheduleSectionEntity the schedule section entity
     * @return the schedule dto
     */
    @Mappings({
            @Mapping(target = "trainDto", source = "scheduleSectionEntity"),
            @Mapping(target = "sectionDtoId", source = "scheduleSectionEntity.sectionEntity.id")
    })
    ScheduleSectionDto toDto(ScheduleSectionEntity scheduleSectionEntity);

    /**
     * To entity schedule section entity.
     *
     * @param scheduleSectionDto the schedule section dto
     * @return the schedule section entity
     */
    @Mappings({
            @Mapping(target = "trainEntity", source = "scheduleSectionDto.trainDto"),
            @Mapping(target = "ticketEntitySet", ignore = true),
            @Mapping(target = "sectionEntity", ignore = true)
    })
    ScheduleSectionEntity toEntity(ScheduleSectionDto scheduleSectionDto);

    /**
     * To dto list list.
     *
     * @param scheduleSectionEntityList the schedule section entity list
     * @return the list
     */
    List<ScheduleSectionDto> toDtoList(List<ScheduleSectionEntity> scheduleSectionEntityList);

    /**
     * To entity list list.
     *
     * @param scheduleSectionDtoList the schedule section dto list
     * @return the list
     */
    List<ScheduleSectionEntity> toEntityList(List<ScheduleSectionDto> scheduleSectionDtoList);

}