package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.TrainDto;
import org.tsystems.javaschool.model.entity.TrainEntity;

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

}
