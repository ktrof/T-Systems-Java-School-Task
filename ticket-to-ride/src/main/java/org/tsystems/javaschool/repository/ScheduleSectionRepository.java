package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.model.entity.ScheduleSectionEntity;
import org.tsystems.javaschool.model.entity.SectionEntity;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * The interface Schedule section repository.
 *
 * @author Trofim Kremen
 */
public interface ScheduleSectionRepository {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<ScheduleSectionEntity> findAll();

    /**
     * Find by train list.
     *
     * @param trainEntity the train entity
     * @param rideDate    the ride date
     * @return the list
     */
    List<ScheduleSectionEntity> findByTrainAndRideDate(TrainEntity trainEntity, LocalDate rideDate);

    /**
     * Find by train and section index.
     *
     * @param trainEntity  the train entity
     * @param sectionIndex the section index
     * @return the list
     */
    ScheduleSectionEntity findByTrainAndSectionIndex(TrainEntity trainEntity, int sectionIndex);

    /**
     * Find by section list.
     *
     * @param sectionEntity the section entity
     * @return the list
     */
    List<ScheduleSectionEntity> findBySection(SectionEntity sectionEntity);

    /**
     * Find by id schedule section entity.
     *
     * @param id the id
     * @return the schedule section entity
     */
    ScheduleSectionEntity findById(int id);

    /**
     * Find by departure station id and r ide date list.
     *
     * @param stationEntity the station entity
     * @param rideDate      the ride date
     * @return the list
     */
    List<ScheduleSectionEntity> findByStationAndRideDate(StationEntity stationEntity, LocalDate rideDate);

    /**
     * Add schedule section entity.
     *
     * @param scheduleSectionEntity the schedule section entity
     */
    void add(ScheduleSectionEntity scheduleSectionEntity);

    /**
     * Add iterable.
     *
     * @param scheduleSectionEntityCollection the schedule section entity collection
     */
    void add(Collection<ScheduleSectionEntity> scheduleSectionEntityCollection);

}
