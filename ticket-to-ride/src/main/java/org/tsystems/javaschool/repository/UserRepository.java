package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.RoleEntity;
import org.tsystems.javaschool.model.entity.UserEntity;

import java.util.List;
import java.util.Set;

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
    UserEntity add(UserEntity userEntity);

    /**
     * Update user entity.
     *
     * @param userEntity the user entity
     * @return the user entity
     */
    UserEntity update(UserEntity userEntity);

    /**
     * Remove.
     *
     * @param userEntity the user entity
     * @throws SBBException the sbb exception
     */
    void remove(UserEntity userEntity) throws SBBException;
}
