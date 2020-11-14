package org.tsystems.javaschool.model.dto.ticketschedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketScheduleSectionDto implements Serializable {

    private Integer ticketId;
    private Integer scheduleSectionId;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private int indexWithinTicket;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;


}
