package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.RegistrationFormDto;
import org.tsystems.javaschool.model.dto.UserDto;
import org.tsystems.javaschool.model.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", source = "userEntity.id"),
            @Mapping(target = "login", source = "userEntity.login")
    })
    UserDto toDto(UserEntity userEntity);

    @Mappings({
            @Mapping(target = "id", source = "registrationFormDto.id"),
            @Mapping(target = "login", source = "registrationFormDto.login"),
            @Mapping(target = "password", source = "registrationFormDto.password"),
            @Mapping(target = "passengerEntityList", ignore = true),
            @Mapping(target = "role", ignore = true)
    })
    UserEntity toEntity(RegistrationFormDto registrationFormDto);
}
