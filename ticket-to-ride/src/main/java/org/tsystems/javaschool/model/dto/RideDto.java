package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideDto implements Serializable {

    private TrainDto trainDto;
    private LocalDate rideDate;
    private int ticketsAvailable;
    private boolean cancelled;

}
