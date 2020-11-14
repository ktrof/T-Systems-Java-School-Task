package org.tsystems.javaschool.model.dto.rideschedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tsystems.javaschool.model.dto.train.TrainDto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideScheduleDto implements Serializable {

    private TrainDto trainDto;
    private LocalDate rideDate;
    private ZonedDateTime departure;
    private LocalDate departureDateFact;
    private LocalDate departureDatePlan;
    private ZonedDateTime arrival;
    private LocalDate arrivalDateFact;
    private LocalDate arrivalDatePlan;
    private int minutesDelayed;
    private int indexWithinTrainRoute;

}
