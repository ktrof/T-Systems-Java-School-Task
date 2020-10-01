package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.TrainDto;
import org.tsystems.javaschool.model.entity.TrainEntity;

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
    TrainDto toDto(TrainEntity trainEntity);

    /**
     * To entity train entity.
     *
     * @param trainDto the train dto
     * @return the train entity
     */
    @Mappings({
            @Mapping(target = "calendarEntityList", ignore = true),
            @Mapping(target = "scheduleSectionEntityList", ignore = true)
    })
    TrainEntity toEntity(TrainDto trainDto);

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
