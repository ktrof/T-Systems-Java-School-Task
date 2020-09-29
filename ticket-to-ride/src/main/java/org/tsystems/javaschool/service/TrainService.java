package org.tsystems.javaschool.service;

import org.tsystems.javaschool.model.dto.TrainDto;

import java.util.List;

/**
 * The interface Train service.
 */
public interface TrainService {

    /**
     * Gets all trains.
     *
     * @return the all trains
     */
    List<TrainDto> getAll();

    /**
     * Gets train by id.
     *
     * @param id the id
     * @return the train by id
     */
    TrainDto getById(String id);

    /**
     * Create train train dto.
     *
     * @param trainDto the train dto
     * @return the train dto
     */
    TrainDto save(TrainDto trainDto);

    /**
     * Modify train train dto.
     *
     * @param trainDto the train dto
     * @return the train dto
     */
    TrainDto edit(TrainDto trainDto);

}
