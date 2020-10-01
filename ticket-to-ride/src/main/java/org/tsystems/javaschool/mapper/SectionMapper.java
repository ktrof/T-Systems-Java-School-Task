package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.SectionDto;
import org.tsystems.javaschool.model.entity.SectionEntity;

import java.util.List;

/**
 * The interface Section mapper.
 *
 * @author Trofim Kremen
 */
@Mapper(uses = StationMapper.class)
public interface SectionMapper {

    /**
     * To dto section dto.
     *
     * @param sectionEntity the section entity
     * @return the section dto
     */
    @Mappings({
            @Mapping(target = "stationDtoFrom", source = "sectionEntity.stationEntityFrom"),
            @Mapping(target = "stationDtoTo", source = "sectionEntity.stationEntityTo"),
    })
    SectionDto toDto(SectionEntity sectionEntity);

    /**
     * To entity section entity.
     *
     * @param sectionDto the section dto
     * @return the section entity
     */
    @Mappings({
            @Mapping(target = "stationEntityFrom", source = "sectionDto.stationDtoFrom"),
            @Mapping(target = "stationEntityTo", source = "sectionDto.stationDtoTo"),
            @Mapping(target = "scheduleSectionEntityList", ignore = true)
    })
    SectionEntity toEntity(SectionDto sectionDto);

    /**
     * To dto list list.
     *
     * @param sectionEntityList the section entity list
     * @return the list
     */
    List<SectionDto> toDtoList(List<SectionEntity> sectionEntityList);

    /**
     * To entity list list.
     *
     * @param sectionDtoList the section dto list
     * @return the list
     */
    List<SectionEntity> toEntityList(List<SectionDto> sectionDtoList);
}
