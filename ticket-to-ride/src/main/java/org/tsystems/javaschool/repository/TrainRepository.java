package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.SBBException;
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
     * @throws SBBException the repository exception
     */
    TrainEntity findById(String id) throws SBBException;

    /**
     * Add train entity.
     *
     * @param trainEntity the train entity
     * @return the train entity
     * @throws SBBException the repository exception
     */
    TrainEntity add(TrainEntity trainEntity) throws SBBException;

    /**
     * Update avg speed train entity.
     *
     * @param trainEntity the train entity
     * @return the train entity
     * @throws SBBException the repository exception
     */
    TrainEntity update(TrainEntity trainEntity) throws SBBException;

    /**
     * Remove.
     *
     * @param trainEntity the train entity
     * @throws SBBException the repository exception
     */
    void remove(TrainEntity trainEntity) throws SBBException;

}
