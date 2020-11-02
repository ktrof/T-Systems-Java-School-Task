package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;

import java.util.List;

/**
 * The interface Station repository.
 *
 * @author Trofim Kremen
 */
public interface StationRepository {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<StationEntity> findAll();

    /**
     * Find all by train list.
     *
     * @param trainEntity the train entity
     * @return the list
     */
    List<StationEntity> findAllByTrain(TrainEntity trainEntity);

    /**
     * Find by id station entity.
     *
     * @param id the id
     * @return the station entity
     */
    StationEntity findById(int id);

    /**
     * Find by name station entity.
     *
     * @param name the name
     * @return the station entity
     */
    StationEntity findByName(String name);

    /**
     * Add station entity.
     *
     * @param stationEntity the station entity
     * @return the station entity
     */
    StationEntity add(StationEntity stationEntity);

    /**
     * Update name station entity.
     *
     * @param stationEntity the station entity
     * @return the station entity
     */
    StationEntity update(StationEntity stationEntity);

    /**
     * Close.
     *
     * @param stationEntity the station entity
     */
    void close(StationEntity stationEntity);

    /**
     * Open.
     *
     * @param stationEntity the station entity
     */
    void open(StationEntity stationEntity);

}
