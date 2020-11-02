package org.tsystems.javaschool.service;

import org.tsystems.javaschool.model.dto.StandDto;

import java.time.LocalDate;

/**
 * The interface Station stand service.
 *
 * @author Trofim Kremen
 */
public interface StationStandService {

    /**
     * Gets by station and ride date.
     *
     * @param stationId the station id
     * @param rideDate  the ride date
     * @return the by station and ride date
     */
    StandDto getByStationIdAndRideDate(int stationId, LocalDate rideDate);

}
