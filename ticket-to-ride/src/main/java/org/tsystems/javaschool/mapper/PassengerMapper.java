package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.PassengerDto;
import org.tsystems.javaschool.model.entity.PassengerEntity;

/**
 * The interface Passenger mapper.
 *
 * @author Trofim Kremen
 */
@Mapper
public interface PassengerMapper {

    /**
     * To dto passenger dto.
     *
     * @param passengerEntity the passenger entity
     * @return the passenger dto
     */
    @Mappings({
            @Mapping(target = "userDto", source = "passengerEntity.userEntity")
    })
    PassengerDto toDto(PassengerEntity passengerEntity);

    /**
     * To entity passenger entity.
     *
     * @param passengerDto the passenger dto
     * @return the passenger entity
     */
    @Mappings({
            @Mapping(target = "userEntity", source = "passengerDto.userDto"),
            @Mapping(target = "ticketEntityList", ignore = true)
    })
    PassengerEntity toEntity(PassengerDto passengerDto);

}
