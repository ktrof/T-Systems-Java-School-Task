package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.RegistrationFormDto;
import org.tsystems.javaschool.model.dto.UserDto;
import org.tsystems.javaschool.model.entity.UserEntity;

/**
 * The interface User mapper.
 *
 * @author Trofim Kremen
 */
@Mapper
public interface UserMapper {

    /**
     * To dto user dto.
     *
     * @param userEntity the user entity
     * @return the user dto
     */
    UserDto toDto(UserEntity userEntity);

    /**
     * To entity user entity.
     *
     * @param registrationFormDto the registration form dto
     * @return the user entity
     */
    @Mappings({
            @Mapping(target = "passengerEntityList", ignore = true),
            @Mapping(target = "roleEntity", ignore = true)
    })
    UserEntity toEntity(RegistrationFormDto registrationFormDto);
}
