package org.tsystems.javaschool.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tsystems.javaschool.mapper.UserMapper;
import org.tsystems.javaschool.model.dto.RegistrationFormDto;
import org.tsystems.javaschool.model.dto.UserDto;
import org.tsystems.javaschool.model.entity.RoleEntity;
import org.tsystems.javaschool.model.entity.UserEntity;
import org.tsystems.javaschool.repository.RoleRepository;
import org.tsystems.javaschool.repository.UserRepository;
import org.tsystems.javaschool.service.UserService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Trofim Kremen
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public RegistrationFormDto registerUser(RegistrationFormDto registrationFormDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(registrationFormDto.getLogin());
        userEntity.setEmail(registrationFormDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registrationFormDto.getPassword()));
        Set<RoleEntity> roleEntityCollection = Collections.singleton(roleRepository.findByName("ROLE_USER"));
        userRepository.add(userEntity, roleEntityCollection);
        return registrationFormDto;
    }

    @Override
    public UserDto editUser(UserDto userDto) {
        try {
            userRepository.update(userMapper.toEntity(userDto));
        } catch (Exception e) {
            log.error("Error updating user", e);
        }
        return userDto;
    }
}
