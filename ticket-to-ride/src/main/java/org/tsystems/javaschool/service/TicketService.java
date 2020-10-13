package org.tsystems.javaschool.service;

import org.tsystems.javaschool.model.dto.PassengerDto;
import org.tsystems.javaschool.model.dto.RouteDto;
import org.tsystems.javaschool.model.dto.TicketDto;

/**
 * The interface Ticket service.
 *
 * @author Trofim Kremen
 */
public interface TicketService {

    /**
     * Has passenger boolean.
     *
     * @param routeDto     the route dto
     * @param passengerDto the passenger dto
     * @return the boolean
     */
    boolean hasPassenger(RouteDto routeDto, PassengerDto passengerDto);

    /**
     * Is time left boolean.
     *
     * @param routeDto the route dto
     * @return the boolean
     */
    boolean isTimeLeft(RouteDto routeDto);

    /**
     * Are tickets available boolean.
     *
     * @param routeDto the route dto
     * @return the boolean
     */
    boolean areTicketsAvailable(RouteDto routeDto);

    /**
     * Buy ticket ticket dto.
     *
     * @param routeDto     the route dto
     * @param passengerDto the passenger dto
     * @return the ticket dto
     */
    TicketDto buyTicket(RouteDto routeDto, PassengerDto passengerDto);

}
