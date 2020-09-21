package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.TrainDto;
import org.tsystems.javaschool.model.entity.TrainEntity;

@Mapper(componentModel = "spring")
public interface TrainMapper {

    @Mappings({
            @Mapping(target = "id", source = "trainEntity.id"),
            @Mapping(target = "symbolCode", source = "trainEntity.symbolCode"),
            @Mapping(target = "name", source = "trainEntity.name"),
            @Mapping(target = "avgSpeed", source = "trainEntity.avgSpeed"),
            @Mapping(target = "numberOfSeats", source = "trainEntity.numberOfSeats"),
            @Mapping(target = "stations", source = "trainEntity.trainStationEntities")
    })
    TrainDto toDto(TrainEntity trainEntity);

    @Mappings({
            @Mapping(target = "id", source = "trainDto.id"),
            @Mapping(target = "symbolCode", source = "trainDto.symbolCode"),
            @Mapping(target = "name", source = "trainDto.name"),
            @Mapping(target = "avgSpeed", source = "trainDto.avgSpeed"),
            @Mapping(target = "numberOfSeats", source = "trainDto.numberOfSeats"),
            @Mapping(target = "trainStationEntities", source = "trainDto.stations")
    })
    TrainEntity toEntity(TrainDto trainDto);

}
