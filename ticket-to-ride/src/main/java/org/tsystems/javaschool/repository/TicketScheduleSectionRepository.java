package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.model.entity.ScheduleSectionEntity;
import org.tsystems.javaschool.model.entity.TicketScheduleSectionEntity;

import java.time.LocalDate;
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
     * @param departureDate the departure date
     * @return the ticket schedule section entity
     */
    List<TicketScheduleSectionEntity> findByScheduleSectionIdAndDepartureDate(int id, LocalDate departureDate);

    /**
     * Add ticket schedule section entity.
     *
     * @param ticketScheduleSectionEntity the ticket schedule section entity
     * @return the ticket schedule section entity
     */
    TicketScheduleSectionEntity add(TicketScheduleSectionEntity ticketScheduleSectionEntity);

    /**
     * Add all list.
     *
     * @param ticketScheduleSectionEntityList the ticket schedule section entity list
     * @return the list
     */
    List<TicketScheduleSectionEntity> addAll(List<TicketScheduleSectionEntity> ticketScheduleSectionEntityList);

}
