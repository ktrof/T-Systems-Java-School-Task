package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.model.entity.UserEntity;

import java.util.List;

/**
 * The interface User repository.
 *
 * @Author Trofim Kremen
 */
public interface UserRepository {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<UserEntity> findAll();

    /**
     * Find by id user entity.
     *
     * @param id the id
     * @return the user entity
     */
    UserEntity findById(int id);

    /**
     * Find by login user entity.
     *
     * @param login the login
     * @return the user entity
     */
    UserEntity findByLogin(String login);

    /**
     * Find by email user entity.
     *
     * @param email the email
     * @return the user entity
     */
    UserEntity findByEmail(String email);

    /**
     * Add user entity.
     *
     * @param userEntity the user entity
     * @return the user entity
     */
    void add(UserEntity userEntity);

    /**
     * Update user entity.
     *
     * @param userEntity the user entity
     * @return the user entity
     */
    void update(UserEntity userEntity);

}
