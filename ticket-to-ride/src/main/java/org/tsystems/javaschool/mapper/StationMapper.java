package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.StationDto;
import org.tsystems.javaschool.model.entity.StationEntity;

@Mapper(componentModel = "spring")
public interface StationMapper {

    @Mappings({
            @Mapping(target = "id", source = "stationEntity.id"),
            @Mapping(target = "name", source = "stationEntity.name"),
            @Mapping(target = "latitude", source = "stationEntity.latitude"),
            @Mapping(target = "longitude", source = "stationEntity.longitude"),
            @Mapping(target = "timezone", source = "stationEntity.timezone"),
            @Mapping(target = "trains", source = "stationEntity.trainStationEntities")
    })
    StationDto toDto(StationEntity stationEntity);

    @Mappings({
            @Mapping(target = "id", source = "stationDto.id"),
            @Mapping(target = "name", source = "stationDto.name"),
            @Mapping(target = "latitude", source = "stationDto.latitude"),
            @Mapping(target = "longitude", source = "stationDto.longitude"),
            @Mapping(target = "timezone", source = "stationDto.timezone"),
            @Mapping(target = "trainStationEntities", source = "stationDto.trains")
    })
    StationEntity toEntity(StationDto stationDto);
}
