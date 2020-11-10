package org.tsystems.javaschool.model.dto.stand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String departureTime;
    private String arrivalTime;

}
