package org.tsystems.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tsystems.javaschool.model.dto.UserDto;
import org.tsystems.javaschool.model.entity.UserEntity;

import java.util.List;

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
     * @param userDto the user dto
     * @return the user entity
     */
    @Mappings({
            @Mapping(target = "roleEntitySet", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "passengerEntityList", ignore = true)
    })
    UserEntity toEntity(UserDto userDto);

    /**
     * To dto list list.
     *
     * @param userEntityList the user entity list
     * @return the list
     */
    List<UserDto> toDtoList(List<UserEntity> userEntityList);

    /**
     * To entity list list.
     *
     * @param userDtoList the user dto list
     * @return the list
     */
    List<UserEntity> toEntityList(List<UserDto> userDtoList);
}
