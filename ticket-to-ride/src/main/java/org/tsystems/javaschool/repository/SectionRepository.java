package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.RepositoryException;
import org.tsystems.javaschool.model.entity.SectionEntity;
import org.tsystems.javaschool.model.entity.StationEntity;

import java.util.List;

/**
 * The interface Section repository.
 *
 * @author Trofim Kremen
 */
public interface SectionRepository {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<SectionEntity> findAll();

    /**
     * Find by id section entity.
     *
     * @param id the id
     * @return the section entity
     * @throws RepositoryException the repository exception
     */
    SectionEntity findById(int id) throws RepositoryException;

    /**
     * Add section entity.
     *
     * @param sectionEntity the section entity
     * @return the section entity
     * @throws RepositoryException the repository exception
     */
    SectionEntity add(SectionEntity sectionEntity) throws RepositoryException;

    /**
     * Update stations section entity.
     *
     * @param stationFrom   the station from
     * @param stationTo     the station to
     * @param sectionEntity the section entity
     * @return the section entity
     * @throws RepositoryException the repository exception
     */
    SectionEntity updateStations(StationEntity stationFrom, StationEntity stationTo, SectionEntity sectionEntity) throws RepositoryException;

    /**
     * Remove.
     *
     * @param sectionEntity the section entity
     * @throws RepositoryException the repository exception
     */
    void remove(SectionEntity sectionEntity) throws RepositoryException;

}
