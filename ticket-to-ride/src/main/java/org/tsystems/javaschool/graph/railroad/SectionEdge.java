package org.tsystems.javaschool.graph.railroad;

import org.tsystems.javaschool.graph.Edge;
import org.tsystems.javaschool.model.dto.section.SectionDto;
import org.tsystems.javaschool.model.entity.TrainEntity;

import java.time.LocalDate;

/**
 * The interface Section edge.
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
     * Gets ride date.
     *
     * @return the ride date
     */
    LocalDate getRideDate();

    /**
     * Gets ticket count available.
     *
     * @return the ticket count available
     */
    int getTicketCountAvailable();

    /**
     * Gets train.
     *
     * @return the train
     */
    TrainEntity getTrain();

    /**
     * To dto section dto.
     *
     * @return the section dto
     */
    SectionDto toDto();
}
