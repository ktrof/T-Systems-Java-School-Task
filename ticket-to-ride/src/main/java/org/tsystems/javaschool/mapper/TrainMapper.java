package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.train.AddTrainFormDto;
import org.tsystems.javaschool.model.dto.train.TrainDto;
import org.tsystems.javaschool.model.dto.train.TrainType;
import org.tsystems.javaschool.model.entity.TrainEntity;

import java.util.Arrays;
import java.util.List;

/**
 * The interface Train mapper.
 *
 * @author Trofim Kremen
 */
@Mapper
public interface TrainMapper {

    /**
     * To dto train dto.
     *
     * @param trainEntity the train entity
     * @return the train dto
     */
    @Mappings({
            @Mapping(target = "type", source = "trainEntity.type")
    })
    TrainDto toDto(TrainEntity trainEntity);

    /**
     * To entity train entity.
     *
     * @param trainDto the train dto
     * @return the train entity
     */
    @Mappings({
            @Mapping(target = "type", source = "trainDto.type.typeOfString"),
            @Mapping(target = "rideEntityList", ignore = true),
            @Mapping(target = "scheduleSectionEntityList", ignore = true),
            @Mapping(target = "rideScheduleEntityList", ignore = true)
    })
    TrainEntity toEntity(TrainDto trainDto);

    /**
     * To entity train entity.
     *
     * @param trainFormDto the train form dto
     * @return the train entity
     */
    @Mappings({
            @Mapping(target = "type", source = "trainFormDto.type.typeOfString"),
            @Mapping(target = "rideEntityList", ignore = true),
            @Mapping(target = "scheduleSectionEntityList", ignore = true),
            @Mapping(target = "rideScheduleEntityList", ignore = true)
    })
    TrainEntity toEntity(AddTrainFormDto trainFormDto);

    /**
     * To dto list list.
     *
     * @param trainEntityList the train entity list
     * @return the list
     */
    List<TrainDto> toDtoList(List<TrainEntity> trainEntityList);

    /**
     * To entity list list.
     *
     * @param trainDtoList the train dto list
     * @return the list
     */
    List<TrainEntity> toEntityList(List<TrainDto> trainDtoList);

}
