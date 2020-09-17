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
            @Mapping(target = "name", source = "trainEntity.name"),
            @Mapping(target = "tonnage", source = "trainEntity.tonnage"),
            @Mapping(target = "technicalSpeed", source = "trainEntity.technicalSpeed")
    })
    TrainDto toDto(TrainEntity trainEntity);

    @Mappings({
            @Mapping(target = "id", source = "trainDto.id"),
            @Mapping(target = "name", source = "trainDto.name"),
            @Mapping(target = "tonnage", source = "trainDto.tonnage"),
            @Mapping(target = "technicalSpeed", source = "trainDto.technicalSpeed")
    })
    TrainEntity toEntity(TrainDto trainDto);

}
