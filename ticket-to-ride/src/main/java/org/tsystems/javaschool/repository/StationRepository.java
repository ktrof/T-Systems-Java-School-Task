package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.StationEntity;

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
     * Find by id station entity.
     *
     * @param id the id
     * @return the station entity
     * @throws SBBException the repository exception
     */
    StationEntity findById(int id) throws SBBException;

    /**
     * Find by name station entity.
     *
     * @param name the name
     * @return the station entity
     * @throws SBBException the repository exception
     */
    StationEntity findByName(String name) throws SBBException;

    /**
     * Add station entity.
     *
     * @param stationEntity the station entity
     * @return the station entity
     * @throws SBBException the repository exception
     */
    StationEntity add(StationEntity stationEntity) throws SBBException;

    /**
     * Update name station entity.
     *
     * @param stationEntity the station entity
     * @return the station entity
     * @throws SBBException the repository exception
     */
    StationEntity update(StationEntity stationEntity) throws SBBException;

    /**
     * Remove.
     *
     * @param stationEntity the station entity
     * @throws SBBException the repository exception
     */
    void remove(StationEntity stationEntity) throws SBBException;

}
