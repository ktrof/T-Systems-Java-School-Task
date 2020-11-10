package org.tsystems.javaschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandRowDto implements Serializable {

    private String trainNumber;
    private boolean isCancelled;
    private int minutesDelayed;
    private String departureTime;
    private String departureStationName;
    private String arrivalTime;
    private String destinationStationName;

}
