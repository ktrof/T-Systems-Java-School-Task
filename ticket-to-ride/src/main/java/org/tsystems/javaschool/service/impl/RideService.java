package org.tsystems.javaschool.service.impl;

import org.tsystems.javaschool.model.dto.ride.RideDto;
import org.tsystems.javaschool.model.dto.train.TrainDto;

import java.time.LocalDate;

/**
 * The interface Ride service.
 *
 * @author Trofim Kremen
 */
public interface RideService {

    /**
     * Gets by train and ride date.
     *
     * @param trainDto the train dto
     * @param rideDate the ride date
     * @return the by train and ride date
     */
    RideDto getByTrainAndRideDate(TrainDto trainDto, LocalDate rideDate);

}
