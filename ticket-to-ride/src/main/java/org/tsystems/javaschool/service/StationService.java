package org.tsystems.javaschool.service;

import org.tsystems.javaschool.model.dto.AddStationFormDto;
import org.tsystems.javaschool.model.dto.SectionDto;
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
     * Gets by name.
     *
     * @param name the name
     * @return the by name
     */
    StationDto getByName(String name);

    /**
     * Create station station dto.
     *
     * @param stationFormDto the station form dto
     * @return the station dto
     */
    AddStationFormDto save(AddStationFormDto stationFormDto);

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
