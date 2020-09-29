package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.PassengerEntity;
import org.tsystems.javaschool.model.entity.TicketEntity;

import java.util.List;

/**
 * The interface Ticket repository.
 *
 * @author Trofim Kremen
 */
public interface TicketRepository {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<TicketEntity> findAll();

    /**
     * Find by passenger list.
     *
     * @param passengerEntity the passenger entity
     * @return the list
     * @throws SBBException the repository exception
     */
    List<TicketEntity> findByPassenger(PassengerEntity passengerEntity) throws SBBException;

    /**
     * Find by passenger name and mobile list.
     *
     * @param firstName    the first name
     * @param secondName   the second name
     * @param mobileNumber the mobile number
     * @return the list
     * @throws SBBException the repository exception
     */
    List<TicketEntity> findByPassengerNameAndMobile(String firstName, String secondName, String mobileNumber) throws SBBException;

    /**
     * Find by id ticket entity.
     *
     * @param id the id
     * @return the ticket entity
     * @throws SBBException the repository exception
     */
    TicketEntity findById(int id) throws SBBException;

    /**
     * Add ticket entity.
     *
     * @param ticketEntity the ticket entity
     * @return the ticket entity
     * @throws SBBException the repository exception
     */
    TicketEntity add(TicketEntity ticketEntity) throws SBBException;

    /**
     * Remove.
     *
     * @param ticketEntity the ticket entity
     * @throws SBBException the repository exception
     */
    void remove(TicketEntity ticketEntity) throws SBBException;

}
