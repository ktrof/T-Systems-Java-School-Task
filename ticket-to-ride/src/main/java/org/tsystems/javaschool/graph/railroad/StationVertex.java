package org.tsystems.javaschool.graph.railroad;

import org.tsystems.javaschool.graph.Vertex;
import org.tsystems.javaschool.model.dto.StationDto;

import java.time.ZoneId;

/**
 * The interface Station vertex.
 *
 * @author Trofim Kremen
 */
public interface StationVertex extends Vertex<StationVertex, SectionEdge> {

    /**
     * Gets id.
     *
     * @return the id
     */
    int getId();

    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    double getLatitude();

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    double getLongitude();

    /**
     * Gets timezone.
     *
     * @return the timezone
     */
    ZoneId getTimezone();

    /**
     * To dto station dto.
     *
     * @return the station dto
     */
    StationDto toDto();
}
