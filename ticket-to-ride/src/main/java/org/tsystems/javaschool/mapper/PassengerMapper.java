package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.PassengerDto;
import org.tsystems.javaschool.model.entity.PassengerEntity;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    @Mappings({
            @Mapping(target = "userDto", source = "passengerEntity.userEntity")
    })
    PassengerDto toDto(PassengerEntity passengerEntity);

    @Mappings({
            @Mapping(target = "userEntity", source = "passengerDto.userDto"),
            @Mapping(target = "ticketEntityList", ignore = true)
    })
    PassengerEntity toEntity(PassengerDto passengerDto);

}
