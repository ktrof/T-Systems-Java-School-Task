package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.RepositoryException;
import org.tsystems.javaschool.model.entity.UserEntity;

import java.util.List;

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
     * @throws RepositoryException the repository exception
     */
    UserEntity findById(int id) throws RepositoryException;

    /**
     * Find by login user entity.
     *
     * @param login the login
     * @return the user entity
     * @throws RepositoryException the repository exception
     */
    UserEntity findByLogin(String login) throws RepositoryException;

    /**
     * Add user entity.
     *
     * @param userEntity the user entity
     * @return the user entity
     * @throws RepositoryException the repository exception
     */
    UserEntity add(UserEntity userEntity) throws RepositoryException;

    /**
     * Update login user entity.
     *
     * @param login      the login
     * @param userEntity the user entity
     * @return the user entity
     * @throws RepositoryException the repository exception
     */
    UserEntity updateLogin(String login, UserEntity userEntity) throws RepositoryException;

    /**
     * Update password user entity.
     *
     * @param password   the password
     * @param userEntity the user entity
     * @return the user entity
     */
    UserEntity updatePassword(String password, UserEntity userEntity);

    /**
     * Remove.
     *
     * @param userEntity the user entity
     * @throws RepositoryException the repository exception
     */
    void remove(UserEntity userEntity) throws RepositoryException;
}
