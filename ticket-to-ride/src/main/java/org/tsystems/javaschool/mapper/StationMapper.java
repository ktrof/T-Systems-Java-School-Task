package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.StationDto;
import org.tsystems.javaschool.model.entity.StationEntity;

import java.util.List;

/**
 * The interface Station mapper.
 *
 * @author Trofim Kremen
 */
@Mapper
public interface StationMapper {

    /**
     * To dto station dto.
     *
     * @param stationEntity the station entity
     * @return the station dto
     */
    StationDto toDto(StationEntity stationEntity);

    /**
     * To entity station entity.
     *
     * @param stationDto the station dto
     * @return the station entity
     */
    @Mappings({
            @Mapping(target = "sectionEntityList", ignore = true)
    })
    StationEntity toEntity(StationDto stationDto);

    /**
     * To dto list list.
     *
     * @param stationEntityList the station entity list
     * @return the list
     */
    List<StationDto> toDtoList(List<StationEntity> stationEntityList);

    /**
     * To entity list list.
     *
     * @param stationDtoList the station dto list
     * @return the list
     */
    List<StationEntity> toEntityList(List<StationDto> stationDtoList);

}
