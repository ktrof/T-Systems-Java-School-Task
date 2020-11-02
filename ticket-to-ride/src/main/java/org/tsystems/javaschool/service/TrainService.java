package org.tsystems.javaschool.service;

import org.tsystems.javaschool.model.dto.AddTrainFormDto;
import org.tsystems.javaschool.model.dto.RideScheduleDto;
import org.tsystems.javaschool.model.dto.TrainDto;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Train service.
 */
public interface TrainService {

    /**
     * Gets all trains.
     *
     * @return the all trains
     */
    List<TrainDto> getAll();

    /**
     * Gets train by id.
     *
     * @param id the id
     * @return the train by id
     */
    TrainDto getById(String id);

    /**
     * Create train train dto.
     *
     * @param trainFormDto the train form dto
     * @return the train dto
     */
    AddTrainFormDto save(AddTrainFormDto trainFormDto);

    /**
     * Modify train train dto.
     *
     * @param trainDto the train dto
     * @return the train dto
     */
    TrainDto edit(TrainDto trainDto);

    /**
     * Cancel train.
     *
     * @param trainDto the train dto
     */
    void cancelTrain(TrainDto trainDto);

    /**
     * Cancel ride.
     *
     * @param trainDto the train dto
     * @param rideDate the ride date
     */
    void cancelRide(TrainDto trainDto, LocalDate rideDate);

    /**
     * Restart train.
     *
     * @param trainDto the train dto
     */
    void restartTrain(TrainDto trainDto);

    /**
     * Restart ride.
     *
     * @param trainDto the train dto
     * @param rideDate the ride date
     */
    void restartRide(TrainDto trainDto, LocalDate rideDate);

    /**
     * Delay train.
     *
     * @param trainId         the train id
     * @param rideScheduleDto the train schedule dto
     * @param minutesDelayed  the minutes delayed
     */
    void delayTrain(String trainId, RideScheduleDto rideScheduleDto, int minutesDelayed);
}
