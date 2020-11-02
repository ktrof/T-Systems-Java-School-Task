package org.tsystems.javaschool.model.dto;

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
public class StandRowDto {

    private String trainNumber;
    private String trainStatus;
    private ZonedDateTime departureTime;
    private String departureStationName;
    private ZonedDateTime arrivalTime;
    private String destinationStationName;

}
