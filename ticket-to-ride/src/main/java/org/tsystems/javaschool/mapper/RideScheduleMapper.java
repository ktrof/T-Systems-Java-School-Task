package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.RideScheduleDto;
import org.tsystems.javaschool.model.entity.RideScheduleEntity;

import java.util.List;

/**
 * The interface Train schedule mapper.
 *
 * @author Trofim Kremen
 */
@Mapper(uses = TrainMapper.class)
public interface RideScheduleMapper {

    /**
     * To entity train schedule entity.
     *
     * @param rideScheduleDto the train schedule dto
     * @return the train schedule entity
     */
    @Mappings({
            @Mapping(target = "trainEntity", source = "rideScheduleDto.trainDto")
    })
    RideScheduleEntity toEntity(RideScheduleDto rideScheduleDto);

    /**
     * To entity list list.
     *
     * @param rideScheduleDtoList the train schedule dto list
     * @return the list
     */
    List<RideScheduleEntity> toEntityList(List<RideScheduleDto> rideScheduleDtoList);

}
