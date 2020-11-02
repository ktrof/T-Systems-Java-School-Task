package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.model.entity.RideEntity;
import org.tsystems.javaschool.model.entity.RideScheduleEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * The interface Calendar repository.
 *
 * @author Trofim Kremen
 */
public interface RideRepository {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<RideEntity> findAll();

    /**
     * Find all by train list.
     *
     * @param trainEntity the train entity
     * @return the list
     */
    List<RideEntity> findAllByTrain(TrainEntity trainEntity);

    /**
     * Find by train at date calendar entity.
     *
     * @param trainEntity the train entity
     * @param rideDate    the ride date
     * @return the calendar entity
     */
    RideEntity findByTrainAndDate(TrainEntity trainEntity, LocalDate rideDate);

    /**
     * Add calendar entity.
     *
     * @param rideEntity the calendar entity
     * @return the calendar entity
     */
    RideEntity add(RideEntity rideEntity);

    /**
     * Add iterable.
     *
     * @param rideEntityCollection the calendar entity collection
     * @return the iterable
     */
    Iterable<RideEntity> add(Collection<RideEntity> rideEntityCollection);

    /**
     * Cancel ride.
     *
     * @param rideEntity the calendar entity
     */
    void cancelRide(RideEntity rideEntity);

    /**
     * Cancel all rides.
     *
     * @param rideEntityCollection the calendar entity collection
     */
    void cancelAllRides(Collection<RideEntity> rideEntityCollection);

    /**
     * Restart ride.
     *
     * @param rideEntity the calendar entity
     */
    void restartRide(RideEntity rideEntity);

    /**
     * Restart all rides.
     *
     * @param rideEntityCollection the calendar entity collection
     */
    void restartAllRides(Collection<RideEntity> rideEntityCollection);

}
