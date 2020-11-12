package org.tsystems.javaschool.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.tsystems.javaschool.mapper.UserMapper;
import org.tsystems.javaschool.mapper.UserMapperImpl;
import org.tsystems.javaschool.model.dto.user.RegistrationFormDto;
import org.tsystems.javaschool.model.dto.user.UserDto;
import org.tsystems.javaschool.model.entity.RoleEntity;
import org.tsystems.javaschool.model.entity.UserEntity;
import org.tsystems.javaschool.repository.RoleRepository;
import org.tsystems.javaschool.repository.UserRepository;
import org.tsystems.javaschool.service.UserService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author Trofim Kremen
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class UserServiceImplTest {

    UserService userService;

    @Mock UserRepository userRepository;
    @Mock RoleRepository roleRepository;
    @Spy  UserMapper userMapper = new UserMapperImpl();
    @Mock PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        userService = new UserServiceImpl(userRepository, roleRepository, userMapper, passwordEncoder);
    }

    private UserEntity getTestUserEntity() {
        return UserEntity.builder()
                .id(1)
                .login("test")
                .email("test@mail.com")
                .build();
    }

    private RoleEntity getTestRoleEntity() {
        return RoleEntity.builder().build();
    }

    private RegistrationFormDto getTestRegistrationForm() {
        return RegistrationFormDto.builder()
                .login("test2")
                .email("test2@mail.com")
                .confirmEmail("test2@mail.com")
                .password("test")
                .confirmPassword("test").build();
    }

    @Test
    public void testGetAll() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(getTestUserEntity()));
        List<UserDto> result = userService.getAll();
        assertEquals(1, result.size());
        UserDto dto = result.get(0);
        assertEquals(getTestUserEntity().getId(), dto.getId());
        assertEquals(getTestUserEntity().getLogin(), dto.getLogin());
    }

    @Test
    public void testGetById() {
        when(userRepository.findById(anyInt())).thenReturn(getTestUserEntity());
        UserDto result = userService.getById(1);
        assertEquals(getTestUserEntity().getId(), result.getId());
        assertEquals(getTestUserEntity().getLogin(), result.getLogin());
    }

    @Test
    public void testGetByLogin() {
        when(userRepository.findByLogin(anyString())).thenReturn(getTestUserEntity());
        UserDto result = userService.getByLogin("test");
        assertEquals(getTestUserEntity().getId(), result.getId());
        assertEquals(getTestUserEntity().getLogin(), result.getLogin());
    }

    @Test
    public void testGetByEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(getTestUserEntity());
        UserDto result = userService.getByEmail("test@mail.com");
        assertEquals(getTestUserEntity().getId(), result.getId());
        assertEquals(getTestUserEntity().getLogin(), result.getLogin());
    }

    @Test
    public void testRegisterUser() {
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("protec");
        when(roleRepository.findByName(anyString())).thenReturn(getTestRoleEntity());
        userService.registerUser(getTestRegistrationForm());
        verify(userRepository, only()).add(any(UserEntity.class));
    }

}
