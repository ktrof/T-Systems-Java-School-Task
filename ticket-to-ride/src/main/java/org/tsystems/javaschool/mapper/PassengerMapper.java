package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.passenger.PassengerDto;
import org.tsystems.javaschool.model.dto.passenger.PassengerFormDto;
import org.tsystems.javaschool.model.entity.PassengerEntity;

import java.util.List;

/**
 * The interface Passenger mapper.
 *
 * @author Trofim Kremen
 */
@Mapper(uses = {UserMapper.class})
public interface PassengerMapper {

    /**
     * To dto passenger dto.
     *
     * @param passengerEntity the passenger entity
     * @return the passenger dto
     */
    PassengerDto toDto(PassengerEntity passengerEntity);

    /**
     * To entity passenger entity.
     *
     * @param passengerFormDto the passenger dto
     * @return the passenger entity
     */
    @Mappings({
            @Mapping(target = "userEntity", ignore = true),
            @Mapping(target = "ticketEntityList", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    PassengerEntity toEntity(PassengerFormDto passengerFormDto);

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
     * @param passengerFormDtoList the passenger dto list
     * @return the list
     */
    List<PassengerEntity> toEntityList(List<PassengerFormDto> passengerFormDtoList);
}
