package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsystems.javaschool.mapper.UserMapper;
import org.tsystems.javaschool.model.dto.user.RegistrationFormDto;
import org.tsystems.javaschool.model.dto.user.UpdateUserFormDto;
import org.tsystems.javaschool.model.dto.user.UserDto;
import org.tsystems.javaschool.model.entity.UserEntity;
import org.tsystems.javaschool.repository.RoleRepository;
import org.tsystems.javaschool.repository.UserRepository;
import org.tsystems.javaschool.service.UserService;

import java.util.*;

/**
 * @author Trofim Kremen
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAll() {
        List<UserDto> userDtoList = null;
        try {
            userDtoList = userMapper.toDtoList(userRepository.findAll());
        } catch (Exception e) {
            log.error("Error getting all the users", e);
        }
        return userDtoList;
    }

    @Override
    @Transactional
    public UserDto getByLogin(String login) {
        UserDto userDto = null;
        try {
            userDto = userMapper.toDto(userRepository.findByLogin(login));
        } catch (Exception e) {
            log.error("Error getting a user by login", e);
        }
        return userDto;
    }

    @Override
    @Transactional
    public UserDto getByEmail(String email) {
        UserDto userDto = null;
        try {
            userDto = userMapper.toDto(userRepository.findByEmail(email));
        } catch (Exception e) {
            log.error("Error getting a user by email");
        }
        return userDto;
    }

    @Override
    public UserDto getById(int id) {
        UserDto userDto = null;
        try {
            userDto = userMapper.toDto(userRepository.findById(id));
        } catch (Exception e) {
            log.error("Error getting a user by id", e);
        }
        return userDto;
    }

    @Override
    @Transactional
    public RegistrationFormDto registerUser(RegistrationFormDto registrationFormDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(registrationFormDto.getLogin());
        userEntity.setEmail(registrationFormDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registrationFormDto.getPassword()));
        userEntity.setRoleEntitySet(Collections.singleton(roleRepository.findByName("ROLE_USER")));
        userRepository.add(userEntity);
        return registrationFormDto;
    }

    @Override
    @Transactional
    public UserDto editUser(UpdateUserFormDto userDto) {
        UserDto updatedUserDto = null;
        try {
            UserEntity updatedUserEntity = userRepository.findById(userDto.getId());
            updatedUserEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
            updatedUserEntity.setLogin(userDto.getLogin());
            updatedUserEntity.setEmail(userDto.getEmail());
            updatedUserEntity.setFirstName(userDto.getFirstName());
            updatedUserEntity.setSecondName(userDto.getSecondName());
            updatedUserEntity.setBirthDate(userDto.getBirthDate());
            updatedUserEntity.setMobileNumber(userDto.getMobileNumber());
            userRepository.update(updatedUserEntity);
            updatedUserDto = userMapper.toDto(updatedUserEntity);
        } catch (Exception e) {
            log.error("Error updating user", e);
        }
        return updatedUserDto;
    }
}
