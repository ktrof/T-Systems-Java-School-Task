package org.tsystems.javaschool.service;

import org.tsystems.javaschool.model.dto.RegistrationFormDto;
import org.tsystems.javaschool.model.dto.UserDto;

import java.util.List;

/**
 * The interface User service.
 *
 * @author Trofim Kremen
 */
public interface UserService {

    /**
     * Gets all.
     *
     * @return the all
     */
    List<UserDto> getAll();

    /**
     * Gets by login.
     *
     * @param login the login
     * @return the by login
     */
    UserDto getByLogin(String login);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    UserDto getById(int id);

    /**
     * Register user registration form dto.
     *
     * @param registrationFormDto the registration form dto
     * @return the registration form dto
     */
    RegistrationFormDto registerUser(RegistrationFormDto registrationFormDto);

    /**
     * Edit user user dto.
     *
     * @param userDto the user dto
     * @return the user dto
     */
    UserDto editUser(UserDto userDto);

}
