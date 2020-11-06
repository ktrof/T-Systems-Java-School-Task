package org.tsystems.javaschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandUpdateDto {

    private String stationName;
    private boolean isStationClosed;
    private String trainNumber;
    private boolean isTrainCancelled;
    private int minutesDelayed;
    private ZonedDateTime departureTime;
    private ZonedDateTime arrivalTime;

}
