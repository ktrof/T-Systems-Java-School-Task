package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.model.entity.TicketScheduleSectionEntity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * The interface Ticket schedule section repository.
 *
 * @author Trofim Kremen
 */
public interface TicketScheduleSectionRepository {

    /**
     * Find by schedule section and departure date ticket schedule section entity.
     *
     * @param id            the id
     * @param rideDate the departure date
     * @return the ticket schedule section entity
     */
    List<TicketScheduleSectionEntity> findByScheduleSectionIdAndRideDate(int id, LocalDate rideDate);

    /**
     * Add ticket schedule section entity.
     *
     * @param ticketScheduleSectionEntity the ticket schedule section entity
     */
    void add(TicketScheduleSectionEntity ticketScheduleSectionEntity);

    /**
     * Add iterable.
     *
     * @param ticketScheduleSectionEntityCollection the ticket schedule section entity collection
     */
    void add(Collection<TicketScheduleSectionEntity> ticketScheduleSectionEntityCollection);

}
