package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.TrainStationDto;
import org.tsystems.javaschool.model.entity.TrainStationEntity;

@Mapper(componentModel = "spring")
public interface TrainStationMapper {

    @Mappings({
            @Mapping(target = "id", source = "trainStationEntity.id"),
            @Mapping(target = "train", source = "trainStationEntity.trainEntity"),
            @Mapping(target = "station", source = "trainStationEntity.stationEntity"),
            @Mapping(target = "arrival", source = "trainStationEntity.arrival"),
            @Mapping(target = "waitingTime", source = "trainStationEntity.waitingTime"),
            @Mapping(target = "departure", source = "trainStationEntity.departure"),
    })
    TrainStationDto toDto(TrainStationEntity trainStationEntity);

    @Mappings({
            @Mapping(target = "id", source = "trainStationDto.id"),
            @Mapping(target = "trainEntity", source = "trainStationDto.train"),
            @Mapping(target = "stationEntity", source = "trainStationDto.station"),
            @Mapping(target = "arrival", source = "trainStationDto.arrival"),
            @Mapping(target = "waitingTime", source = "trainStationDto.waitingTime"),
            @Mapping(target = "departure", source = "trainStationDto.departure"),
    })
    TrainStationEntity toEntity(TrainStationDto trainStationDto);

}
