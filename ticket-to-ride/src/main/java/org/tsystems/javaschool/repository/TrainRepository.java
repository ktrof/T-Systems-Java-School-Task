package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.RepositoryException;
import org.tsystems.javaschool.model.entity.TrainEntity;

import java.util.List;

/**
 * The interface Train repository.
 *
 * @author Trofim Kremen
 */
public interface TrainRepository {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<TrainEntity> findAll();

    /**
     * Find by id train entity.
     *
     * @param id the id
     * @return the train entity
     * @throws RepositoryException the repository exception
     */
    TrainEntity findById(int id) throws RepositoryException;

    /**
     * Find by symbol code train entity.
     *
     * @param symbolCode the symbol code
     * @return the train entity
     * @throws RepositoryException the repository exception
     */
    TrainEntity findBySymbolCode(String symbolCode) throws RepositoryException;

    /**
     * Add train entity.
     *
     * @param trainEntity the train entity
     * @return the train entity
     * @throws RepositoryException the repository exception
     */
    TrainEntity add(TrainEntity trainEntity) throws RepositoryException;

    /**
     * Update avg speed train entity.
     *
     * @param speed       the speed
     * @param trainEntity the train entity
     * @return the train entity
     * @throws RepositoryException the repository exception
     */
    TrainEntity updateAvgSpeed(int speed, TrainEntity trainEntity) throws RepositoryException;

    /**
     * Update number of seats train entity.
     *
     * @param numberOfSeats the number of seats
     * @param trainEntity   the train entity
     * @return the train entity
     * @throws RepositoryException the repository exception
     */
    TrainEntity updateNumberOfSeats(int numberOfSeats, TrainEntity trainEntity) throws RepositoryException;

    /**
     * Remove.
     *
     * @param trainEntity the train entity
     * @throws RepositoryException the repository exception
     */
    void remove(TrainEntity trainEntity) throws RepositoryException;

}
