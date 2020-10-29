package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.model.entity.TrainEntity;
import org.tsystems.javaschool.model.entity.TrainScheduleEntity;

import java.time.LocalDate;
import java.util.Collection;

/**
 * The interface Train schedule repository.
 *
 * @author Trofim Kremen
 */
public interface TrainScheduleRepository {

    /**
     * Find by train and section index and arrival date train schedule entity.
     *
     * @param trainEntity           the train entity
     * @param indexWithinTrainRoute the index within train route
     * @param arrivalDate           the arrival date
     * @return the train schedule entity
     */
    TrainScheduleEntity findByTrainAndSectionIndexAndArrivalDate(TrainEntity trainEntity,
                                                                 int indexWithinTrainRoute,
                                                                 LocalDate arrivalDate);

    /**
     * Add train schedule entity.
     *
     * @param trainScheduleEntity the train schedule entity
     * @return the train schedule entity
     */
    TrainScheduleEntity add(TrainScheduleEntity trainScheduleEntity);

    /**
     * Add iterable.
     *
     * @param trainScheduleEntityCollection the train schedule entity collection
     * @return the iterable
     */
    Iterable<TrainScheduleEntity> add(Collection<TrainScheduleEntity> trainScheduleEntityCollection);

    /**
     * Delay.
     *
     * @param trainScheduleEntity the train schedule entity
     * @param minutes             the minutes
     */
    void delay(TrainScheduleEntity trainScheduleEntity, int minutes);

}
