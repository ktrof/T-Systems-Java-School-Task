package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.RepositoryException;
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
     * @throws RepositoryException the repository exception
     */
    StationEntity findById(int id) throws RepositoryException;

    /**
     * Find by name station entity.
     *
     * @param name the name
     * @return the station entity
     * @throws RepositoryException the repository exception
     */
    StationEntity findByName(String name) throws RepositoryException;

    /**
     * Add station entity.
     *
     * @param stationEntity the station entity
     * @return the station entity
     * @throws RepositoryException the repository exception
     */
    StationEntity add(StationEntity stationEntity) throws RepositoryException;

    /**
     * Update name station entity.
     *
     * @param name          the name
     * @param stationEntity the station entity
     * @return the station entity
     * @throws RepositoryException the repository exception
     */
    StationEntity updateName(String name, StationEntity stationEntity) throws RepositoryException;

    /**
     * Remove.
     *
     * @param stationEntity the station entity
     * @throws RepositoryException the repository exception
     */
    void remove(StationEntity stationEntity) throws RepositoryException;

}
