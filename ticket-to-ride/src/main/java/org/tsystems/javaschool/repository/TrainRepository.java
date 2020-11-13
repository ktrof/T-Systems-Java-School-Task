package org.tsystems.javaschool.repository;

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
     */
    TrainEntity findById(String id);

    /**
     * Add train entity.
     *
     * @param trainEntity the train entity
     */
    void add(TrainEntity trainEntity);

    /**
     * Update avg speed train entity.
     *
     * @param trainEntity the train entity
     */
    void update(TrainEntity trainEntity);

    /**
     * Cancel.
     *
     * @param trainEntity the train entity
     */
    void cancel(TrainEntity trainEntity);

    /**
     * Restart.
     *
     * @param trainEntity the train entity
     */
    void restart(TrainEntity trainEntity);

}
