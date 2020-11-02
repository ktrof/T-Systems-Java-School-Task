package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tsystems.javaschool.model.dto.RideDto;
import org.tsystems.javaschool.model.entity.RideEntity;

import java.util.List;

/**
 * The interface Calendar mapper.
 *
 * @author Trofim Kremen
 */
@Mapper(uses = TrainMapper.class)
public interface RideMapper {

    /**
     * To dto calendar dto.
     *
     * @param rideEntity the calendar entity
     * @return the calendar dto
     */
    @Mapping(target = "trainDto", source = "rideEntity.trainEntity")
    RideDto toDto(RideEntity rideEntity);

    /**
     * To dto list.
     *
     * @param rideEntityList the calendar entity list
     * @return the list
     */
    List<RideDto> toDto(List<RideEntity> rideEntityList);

}
