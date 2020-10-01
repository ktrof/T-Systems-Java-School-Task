package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.PassengerDto;
import org.tsystems.javaschool.model.entity.PassengerEntity;

import java.util.List;

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

    /**
     * To dto list list.
     *
     * @param passengerEntityList the passenger entity list
     * @return the list
     */
    List<PassengerDto> toDtoList(List<PassengerEntity> passengerEntityList);

    /**
     * To entity list list.
     *
     * @param passengerDtoList the passenger dto list
     * @return the list
     */
    List<PassengerEntity> toEntityList(List<PassengerDto> passengerDtoList);
}
