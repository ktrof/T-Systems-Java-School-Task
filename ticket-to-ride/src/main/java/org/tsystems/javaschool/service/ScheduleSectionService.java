package org.tsystems.javaschool.service;

import org.tsystems.javaschool.model.dto.schedulesection.ScheduleSectionDto;
import org.tsystems.javaschool.model.dto.station.StationDto;
import org.tsystems.javaschool.model.dto.train.TrainDto;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Schedule section service.
 *
 * @author Trofim Kremen
 */
public interface ScheduleSectionService {

    /**
     * Gets by departure station and ride date.
     *
     * @param departureStation the departure station
     * @param rideDate         the ride date
     * @return the by departure station and ride date
     */
    List<ScheduleSectionDto> getByDepartureStationAndRideDate(StationDto departureStation, LocalDate rideDate);

    /**
     * Gets by destination station and ride date.
     *
     * @param destinationStation the destination station
     * @param rideDate           the ride date
     * @return the by destination station and ride date
     */
    List<ScheduleSectionDto> getByDestinationStationAndRideDate(StationDto destinationStation, LocalDate rideDate);

    /**
     * Gets by train and ride date.
     *
     * @param trainDto the train dto
     * @param rideDate the ride date
     * @return the by train and ride date
     */
    List<ScheduleSectionDto> getByTrainAndRideDate(TrainDto trainDto, LocalDate rideDate);

}
