package org.tsystems.javaschool.graph.railroad;

import org.tsystems.javaschool.graph.Graph;
import org.tsystems.javaschool.model.entity.ScheduleSectionEntity;

import java.time.LocalDate;

/**
 * The interface Railroad graph.
 *
 * @author Trofim Kremen
 */
public interface RailroadGraph extends Graph<StationVertex, SectionEdge> {

    /**
     * Gets station vertex by name.
     *
     * @param name the name
     * @return the station vertex by name
     */
    StationVertex getStationVertexByName(String name);

    int countBoughtTickets(ScheduleSectionEntity scheduleSectionEntity, LocalDate rideDate);

}
