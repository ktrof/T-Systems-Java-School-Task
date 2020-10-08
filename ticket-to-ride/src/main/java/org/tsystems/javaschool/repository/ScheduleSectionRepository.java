package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.model.entity.ScheduleSectionEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;

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
     * @return the list
     */
    List<ScheduleSectionEntity> findByTrain(TrainEntity trainEntity);

    /**
     * Find by id schedule section entity.
     *
     * @param id the id
     * @return the schedule section entity
     */
    ScheduleSectionEntity findById(int id);

    /**
     * Add schedule section entity.
     *
     * @param scheduleSectionEntity the schedule section entity
     * @return the schedule section entity
     */
    ScheduleSectionEntity add(ScheduleSectionEntity scheduleSectionEntity);

    /**
     * Add iterable.
     *
     * @param scheduleSectionEntityCollection the schedule section entity collection
     * @return the iterable
     */
    Iterable<ScheduleSectionEntity> add(Collection<ScheduleSectionEntity> scheduleSectionEntityCollection);

}
