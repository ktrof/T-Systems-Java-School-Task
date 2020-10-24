package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.model.entity.TicketEntity;
import org.tsystems.javaschool.model.entity.UserEntity;

import java.time.LocalDate;
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
     * @param userEntity the user entity
     * @return the list
     */
    List<TicketEntity> findByUser(UserEntity userEntity);

    /**
     * Find by passenger name and mobile list.
     *
     * @param firstName  the first name
     * @param secondName the second name
     * @param birthDate  the birth date
     * @return the list
     */
    List<TicketEntity> findByPassengerNameAndBirthDate(String firstName, String secondName, LocalDate birthDate);

    /**
     * Find by id ticket entity.
     *
     * @param id the id
     * @return the ticket entity
     */
    TicketEntity findById(int id);

    /**
     * Add ticket entity.
     *
     * @param ticketEntity the ticket entity
     * @return the ticket entity
     */
    TicketEntity add(TicketEntity ticketEntity);

}
