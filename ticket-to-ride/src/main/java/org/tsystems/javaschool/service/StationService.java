package org.tsystems.javaschool.service;

import org.tsystems.javaschool.model.dto.StationDto;

import java.util.List;

/**
 * The interface Station service.
 *
 * @author Trofim Kremen
 */
public interface StationService {

    /**
     * Gets all stations.
     *
     * @return the all stations
     */
    List<StationDto> getAll();

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    StationDto getById(int id);

    /**
     * Create station station dto.
     *
     * @param stationDto the station dto
     * @return the station dto
     */
    StationDto save(StationDto stationDto);

    /**
     * Change station name station dto.
     *
     * @param stationDto the station dto
     * @return the station dto
     */
    StationDto edit(StationDto stationDto);

    /**
     * Delete station.
     *
     * @param stationDto the station dto
     */
    void delete(StationDto stationDto);

}
