package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.model.entity.CalendarEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;

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
     * Remove.
     *
     * @param calendarEntity the calendar entity
     */
    void remove(CalendarEntity calendarEntity);

}
