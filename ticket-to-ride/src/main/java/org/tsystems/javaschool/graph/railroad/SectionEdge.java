package org.tsystems.javaschool.graph.railroad;

import org.tsystems.javaschool.graph.Edge;
import org.tsystems.javaschool.model.dto.SectionDto;

/**
 * The interface Section edge.
 *
 * @author Trofim Kremen
 */
public interface SectionEdge extends Edge<StationVertex, SectionEdge> {

    /**
     * Gets id.
     *
     * @return the id
     */
    int getId();

    /**
     * Gets length.
     *
     * @return the length
     */
    double getLength();

    /**
     * To dto schedule section dto.
     *
     * @return the schedule section dto
     */
    SectionDto toDto();
}
