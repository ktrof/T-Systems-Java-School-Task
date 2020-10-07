package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.SectionEntity;
import org.tsystems.javaschool.model.entity.StationEntity;

import java.util.Collection;
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
     */
    SectionEntity findById(int id);

    /**
     * Add section entity.
     *
     * @param sectionEntity the section entity
     * @return the section entity
     */
    SectionEntity add(SectionEntity sectionEntity);

    /**
     * Add iterable.
     *
     * @param sectionEntityCollection the section entity collection
     * @return the iterable
     */
    Iterable<SectionEntity> add(Collection<SectionEntity> sectionEntityCollection);

    /**
     * Update stations section entity.
     *
     * @param stationFrom   the station from
     * @param stationTo     the station to
     * @param sectionEntity the section entity
     * @return the section entity
     */
    SectionEntity updateStations(StationEntity stationFrom, StationEntity stationTo, SectionEntity sectionEntity);

    /**
     * Remove.
     *
     * @param sectionEntity the section entity
     */
    void remove(SectionEntity sectionEntity);

}
