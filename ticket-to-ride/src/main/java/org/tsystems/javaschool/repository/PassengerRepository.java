package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.SBBException;
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
     */
    List<PassengerEntity> findAllByUser(UserEntity userEntity);

    /**
     * Find by name list.
     *
     * @param firstName  the first name
     * @param secondName the second name
     * @return the list
     */
    List<PassengerEntity> findByName(String firstName, String secondName);

    /**
     * Find by id passenger entity.
     *
     * @param id the id
     * @return the passenger entity
     */
    PassengerEntity findById(int id);

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
     */
    PassengerEntity updateName(String firstName, String secondName, PassengerEntity passengerEntity);

    /**
     * Update mobile number passenger entity.
     *
     * @param mobile          the mobile
     * @param passengerEntity the passenger entity
     * @return the passenger entity
     */
    PassengerEntity updateMobileNumber(String mobile, PassengerEntity passengerEntity);

    /**
     * Update email passenger entity.
     *
     * @param email           the email
     * @param passengerEntity the passenger entity
     * @return the passenger entity
     */
    PassengerEntity updateEmail(String email, PassengerEntity passengerEntity);

    /**
     * Remove.
     *
     * @param passengerEntity the passenger entity
     */
    void remove(PassengerEntity passengerEntity);

}
