package org.tsystems.javaschool.service;

import org.tsystems.javaschool.model.dto.PassengerFormDto;
import org.tsystems.javaschool.model.dto.TicketDto;
import org.tsystems.javaschool.model.dto.UserDto;

import java.util.List;

/**
 * The interface Ticket service.
 *
 * @author Trofim Kremen
 */
public interface TicketService {

    /**
     * Gets by user dto.
     *
     * @param login the login
     * @return the by user dto
     */
    List<TicketDto> getByUserLogin(String login);

    /**
     * Has passenger boolean.
     *
     * @param passengerFormDto the passenger dto
     * @return the boolean
     */
    boolean hasPassenger(PassengerFormDto passengerFormDto);

    /**
     * Is time left boolean.
     *
     * @param passengerFormDto the passenger dto
     * @return the boolean
     */
    boolean isTimeLeft(PassengerFormDto passengerFormDto);

    /**
     * Are tickets available boolean.
     *
     * @param passengerFormDto the passenger dto
     * @return the boolean
     */
    boolean areTicketsAvailable(PassengerFormDto passengerFormDto);

    /**
     * Buy ticket ticket dto.
     *
     * @param passengerFormDto the passenger dto
     * @return the ticket dto
     */
    TicketDto buyTicket(PassengerFormDto passengerFormDto);

}
