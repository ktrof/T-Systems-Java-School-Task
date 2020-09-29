package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.SBBException;
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
     * @throws SBBException the repository exception
     */
    SectionEntity findById(int id) throws SBBException;

    /**
     * Add section entity.
     *
     * @param sectionEntity the section entity
     * @return the section entity
     * @throws SBBException the repository exception
     */
    SectionEntity add(SectionEntity sectionEntity) throws SBBException;

    /**
     * Update stations section entity.
     *
     * @param stationFrom   the station from
     * @param stationTo     the station to
     * @param sectionEntity the section entity
     * @return the section entity
     * @throws SBBException the repository exception
     */
    SectionEntity updateStations(StationEntity stationFrom, StationEntity stationTo, SectionEntity sectionEntity) throws SBBException;

    /**
     * Remove.
     *
     * @param sectionEntity the section entity
     * @throws SBBException the repository exception
     */
    void remove(SectionEntity sectionEntity) throws SBBException;

}
