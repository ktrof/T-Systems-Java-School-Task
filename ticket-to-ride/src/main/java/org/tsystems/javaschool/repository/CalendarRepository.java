package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.CalendarEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;

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
     * @throws SBBException the repository exception
     */
    List<CalendarEntity> findAllByTrain(TrainEntity trainEntity) throws SBBException;

    /**
     * Find by id calendar entity.
     *
     * @param id the id
     * @return the calendar entity
     * @throws SBBException the repository exception
     */
    CalendarEntity findById(int id) throws SBBException;

    /**
     * Add calendar entity.
     *
     * @param calendarEntity the calendar entity
     * @return the calendar entity
     * @throws SBBException the repository exception
     */
    CalendarEntity add(CalendarEntity calendarEntity) throws SBBException;

    /**
     * Remove.
     *
     * @param calendarEntity the calendar entity
     * @throws SBBException the repository exception
     */
    void remove(CalendarEntity calendarEntity) throws SBBException;

}
