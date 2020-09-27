package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.RepositoryException;
import org.tsystems.javaschool.model.entity.PassengerEntity;
import org.tsystems.javaschool.model.entity.UserEntity;

import java.util.List;

/**
 * The interface Passenger repository.
 *
 * @author Trofim Kremen
 */
public interface PassengerRepository {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<PassengerEntity> findAll();

    /**
     * Find all by user list.
     *
     * @param userEntity the user entity
     * @return the list
     * @throws RepositoryException the repository exception
     */
    List<PassengerEntity> findAllByUser(UserEntity userEntity) throws RepositoryException;

    /**
     * Find by name list.
     *
     * @param firstName  the first name
     * @param secondName the second name
     * @return the list
     * @throws RepositoryException the repository exception
     */
    List<PassengerEntity> findByName(String firstName, String secondName) throws RepositoryException;

    /**
     * Find by id passenger entity.
     *
     * @param id the id
     * @return the passenger entity
     * @throws RepositoryException the repository exception
     */
    PassengerEntity findById(int id) throws RepositoryException;

    /**
     * Add passenger entity.
     *
     * @param passengerEntity the passenger entity
     * @return the passenger entity
     */
    PassengerEntity add(PassengerEntity passengerEntity);

    /**
     * Update name passenger entity.
     *
     * @param firstName       the first name
     * @param secondName      the second name
     * @param passengerEntity the passenger entity
     * @return the passenger entity
     * @throws RepositoryException the repository exception
     */
    PassengerEntity updateName(String firstName, String secondName, PassengerEntity passengerEntity) throws RepositoryException;

    /**
     * Update mobile number passenger entity.
     *
     * @param mobile          the mobile
     * @param passengerEntity the passenger entity
     * @return the passenger entity
     * @throws RepositoryException the repository exception
     */
    PassengerEntity updateMobileNumber(String mobile, PassengerEntity passengerEntity) throws RepositoryException;

    /**
     * Update email passenger entity.
     *
     * @param email           the email
     * @param passengerEntity the passenger entity
     * @return the passenger entity
     * @throws RepositoryException the repository exception
     */
    PassengerEntity updateEmail(String email, PassengerEntity passengerEntity) throws RepositoryException;

    /**
     * Remove.
     *
     * @param passengerEntity the passenger entity
     * @throws RepositoryException the repository exception
     */
    void remove(PassengerEntity passengerEntity) throws RepositoryException;

}
