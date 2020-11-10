package org.tsystems.javaschool.service;

import org.tsystems.javaschool.model.dto.rideschedule.RideScheduleDto;
import org.tsystems.javaschool.model.dto.train.TrainDto;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface R ide schedule service.
 *
 * @author Trofim Kremen
 */
public interface RideScheduleService {

    /**
     * Gets by train an ride date.
     *
     * @param trainDto the train dto
     * @param rideDate the ride date
     * @return the by train an ride date
     */
    List<RideScheduleDto> getByTrainAndRideDate(TrainDto trainDto, LocalDate rideDate);

}
