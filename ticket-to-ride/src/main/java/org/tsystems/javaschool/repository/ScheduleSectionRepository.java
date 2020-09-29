package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.ScheduleSectionEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;

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
     * @return the list
     * @throws SBBException the repository exception
     */
    List<ScheduleSectionEntity> findByTrain(TrainEntity trainEntity) throws SBBException;

    /**
     * Find by id schedule section entity.
     *
     * @param id the id
     * @return the schedule section entity
     * @throws SBBException the repository exception
     */
    ScheduleSectionEntity findById(int id) throws SBBException;

    /**
     * Add schedule section entity.
     *
     * @param scheduleSectionEntity the schedule section entity
     * @return the schedule section entity
     * @throws SBBException the repository exception
     */
    ScheduleSectionEntity add(ScheduleSectionEntity scheduleSectionEntity) throws SBBException;

}
