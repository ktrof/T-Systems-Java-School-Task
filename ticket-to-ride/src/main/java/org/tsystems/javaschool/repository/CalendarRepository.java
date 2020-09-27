package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.RepositoryException;
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
     * @throws RepositoryException the repository exception
     */
    List<CalendarEntity> findAllByTrain(TrainEntity trainEntity) throws RepositoryException;

    /**
     * Find by id calendar entity.
     *
     * @param id the id
     * @return the calendar entity
     * @throws RepositoryException the repository exception
     */
    CalendarEntity findById(int id) throws RepositoryException;

    /**
     * Add calendar entity.
     *
     * @param calendarEntity the calendar entity
     * @return the calendar entity
     * @throws RepositoryException the repository exception
     */
    CalendarEntity add(CalendarEntity calendarEntity) throws RepositoryException;

    /**
     * Remove.
     *
     * @param calendarEntity the calendar entity
     * @throws RepositoryException the repository exception
     */
    void remove(CalendarEntity calendarEntity) throws RepositoryException;

}
