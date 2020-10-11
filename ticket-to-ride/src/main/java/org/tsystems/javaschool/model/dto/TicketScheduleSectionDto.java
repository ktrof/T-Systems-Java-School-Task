package org.tsystems.javaschool.model.dto;

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

    private int ticketId;
    private int scheduleSectionId;
    private LocalDate departureDate;
    private LocalTime departureTime;

}
