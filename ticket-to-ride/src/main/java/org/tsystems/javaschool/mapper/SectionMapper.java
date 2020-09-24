package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.SectionDto;
import org.tsystems.javaschool.model.entity.SectionEntity;

@Mapper(componentModel = "spring", uses = StationMapper.class)
public interface SectionMapper {

    @Mappings({
            @Mapping(target = "stationDtoFrom", source = "sectionEntity.stationEntityFrom"),
            @Mapping(target = "stationDtoTo", source = "sectionEntity.stationEntityTo")
    })
    SectionDto toDto(SectionEntity sectionEntity);

    @Mappings({
            @Mapping(target = "stationEntityFrom", source = "sectionDto.stationDtoFrom"),
            @Mapping(target = "stationEntityTo", source = "sectionDto.stationDtoTo"),
            @Mapping(target = "ticketEntitySet", ignore = true)
    })
    SectionEntity toEntity(SectionDto sectionDto);
}
