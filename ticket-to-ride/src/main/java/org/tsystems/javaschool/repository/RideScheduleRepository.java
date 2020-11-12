package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.model.entity.RideScheduleEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

/**
 * The interface Train schedule repository.
 *
 * @author Trofim Kremen
 */
public interface RideScheduleRepository {

    /**
     * Find by train and departure date list.
     *
     * @param trainEntity the train entity
     * @param rideDate    the ride date
     * @return the list
     */
    List<RideScheduleEntity> findByTrainAndRideDate(TrainEntity trainEntity, LocalDate rideDate);

    /**
     * Find by train and section index and arrival date train schedule entity.
     *
     * @param trainEntity           the train entity
     * @param indexWithinTrainRoute the index within train route
     * @param arrivalDate           the arrival date
     * @return the train schedule entity
     */
    RideScheduleEntity findByTrainAndSectionIndexAndArrivalDate(TrainEntity trainEntity,
                                                                int indexWithinTrainRoute,
                                                                LocalDate arrivalDate);

    /**
     * Add train schedule entity.
     *
     * @param rideScheduleEntity the train schedule entity
     */
    void add(RideScheduleEntity rideScheduleEntity);

    /**
     * Add iterable.
     *
     * @param rideScheduleEntityCollection the train schedule entity collection
     */
    void add(Collection<RideScheduleEntity> rideScheduleEntityCollection);

    /**
     * Delay.
     *
     * @param rideScheduleEntity the train schedule entity
     * @param arrival            the arrival
     * @param minutes            the minutes
     */
    void delayArrival(RideScheduleEntity rideScheduleEntity, ZonedDateTime arrival, int minutes);

    /**
     * Delay departure.
     *
     * @param rideScheduleEntity the train schedule entity
     * @param departure          the departure
     * @param minutes            the minutes
     */
    void delayDeparture(RideScheduleEntity rideScheduleEntity, ZonedDateTime departure, int minutes);

}
