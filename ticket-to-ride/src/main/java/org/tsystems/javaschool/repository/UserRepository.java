package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.RoleEntity;
import org.tsystems.javaschool.model.entity.UserEntity;

import java.util.List;
import java.util.Set;

/**
 * The interface User repository.
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
     * @throws SBBException the repository exception
     */
    UserEntity findById(int id) throws SBBException;

    /**
     * Find by login user entity.
     *
     * @param login the login
     * @return the user entity
     * @throws SBBException the repository exception
     */
    UserEntity findByLogin(String login) throws SBBException;

    /**
     * Find by email user entity.
     *
     * @param email the email
     * @return the user entity
     * @throws SBBException the sbb exception
     */
    UserEntity findByEmail(String email) throws SBBException;

    /**
     * Add user entity.
     *
     * @param userEntity the user entity
     * @return the user entity
     * @throws SBBException the repository exception
     */
    UserEntity add(UserEntity userEntity) throws SBBException;

    /**
     * Update login user entity.
     *
     * @param userEntity the user entity
     * @return the user entity
     * @throws SBBException the repository exception
     */
    UserEntity update(UserEntity userEntity) throws SBBException;

    /**
     * Remove.
     *
     * @param userEntity the user entity
     * @throws SBBException the repository exception
     */
    void remove(UserEntity userEntity) throws SBBException;
}
