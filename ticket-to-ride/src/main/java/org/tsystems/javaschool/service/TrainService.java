package org.tsystems.javaschool.service;

import org.tsystems.javaschool.model.dto.train.AddTrainFormDto;
import org.tsystems.javaschool.model.dto.rideschedule.RideScheduleDto;
import org.tsystems.javaschool.model.dto.train.DelayFormDto;
import org.tsystems.javaschool.model.dto.train.TrainDto;

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
     * @param trainId the train id
     */
    void cancelTrain(String trainId);

    /**
     * Cancel ride.
     *
     * @param trainId  the train id
     * @param rideDate the ride date
     */
    void cancelRide(String trainId, LocalDate rideDate);

    /**
     * Restart train.
     *
     * @param trainId the train id
     */
    void restartTrain(String trainId);

    /**
     * Restart ride.
     *
     * @param trainId  the train id
     * @param rideDate the ride date
     */
    void restartRide(String trainId, LocalDate rideDate);

    /**
     * Delay train.
     *
     * @param trainId      the train id
     * @param delayFormDto the delay form dto
     */
    void delayTrain(String trainId, DelayFormDto delayFormDto);
}
