package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.model.entity.CalendarEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * The interface Calendar repository.
 *
 * @author Trofim Kremen
 */
public interface CalendarRepository {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<CalendarEntity> findAll();

    /**
     * Find all by train list.
     *
     * @param trainEntity the train entity
     * @return the list
     */
    List<CalendarEntity> findAllByTrain(TrainEntity trainEntity);

    /**
     * Find by train at date calendar entity.
     *
     * @param trainEntity the train entity
     * @param rideDate    the ride date
     * @return the calendar entity
     */
    CalendarEntity findByTrainAndDate(TrainEntity trainEntity, LocalDate rideDate);

    /**
     * Find by id calendar entity.
     *
     * @param id the id
     * @return the calendar entity
     */
    CalendarEntity findById(int id);

    /**
     * Add calendar entity.
     *
     * @param calendarEntity the calendar entity
     * @return the calendar entity
     */
    CalendarEntity add(CalendarEntity calendarEntity);

    /**
     * Add iterable.
     *
     * @param calendarEntityCollection the calendar entity collection
     * @return the iterable
     */
    Iterable<CalendarEntity> add(Collection<CalendarEntity> calendarEntityCollection);

    /**
     * Cancel ride.
     *
     * @param calendarEntity the calendar entity
     */
    void cancelRide(CalendarEntity calendarEntity);

    /**
     * Cancel all rides.
     *
     * @param calendarEntityCollection the calendar entity collection
     */
    void cancelAllRides(Collection<CalendarEntity> calendarEntityCollection);

    /**
     * Restart ride.
     *
     * @param calendarEntity the calendar entity
     */
    void restartRide(CalendarEntity calendarEntity);

    /**
     * Restart all rides.
     *
     * @param calendarEntityCollection the calendar entity collection
     */
    void restartAllRides(Collection<CalendarEntity> calendarEntityCollection);

}
