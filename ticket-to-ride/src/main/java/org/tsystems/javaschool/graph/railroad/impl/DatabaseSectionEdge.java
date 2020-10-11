package org.tsystems.javaschool.graph.railroad.impl;

import lombok.Builder;
import org.tsystems.javaschool.graph.railroad.RailroadGraph;
import org.tsystems.javaschool.graph.railroad.SectionEdge;
import org.tsystems.javaschool.graph.railroad.StationVertex;
import org.tsystems.javaschool.model.dto.SectionDto;
import org.tsystems.javaschool.model.entity.CalendarEntity;
import org.tsystems.javaschool.model.entity.ScheduleSectionEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;

import java.time.LocalDate;

/**
 * @author Trofim Kremen
 */
@Builder
public class DatabaseSectionEdge implements SectionEdge {

    private final RailroadGraph railroadGraph;
    private final StationVertex sourceStationVertex;
    private final ScheduleSectionEntity scheduleSectionEntity;
    private final CalendarEntity calendarEntity;

    @Override
    public int getId() {
        return scheduleSectionEntity.getId();
    }

    @Override
    public double getLength() {
        return scheduleSectionEntity.getSectionEntity().getLength();
    }

    @Override
    public LocalDate getRideDate() {
        return calendarEntity.getRideDate();
    }

    @Override
    public int getTicketCountAvailable() {
        int ticketsBought = railroadGraph.countBoughtTickets(scheduleSectionEntity, calendarEntity.getRideDate());
        return getTrain().getNumberOfSeats() - ticketsBought;
    }

    @Override
    public TrainEntity getTrain() {
        return scheduleSectionEntity.getTrainEntity();
    }

    @Override
    public SectionDto toDto() {
        return SectionDto.builder()
                .id(getId())
                .length(getLength())
                .stationDtoFrom(getSourceVertex().toDto().getName())
                .stationDtoTo(getTargetVertex().toDto().getName())
                .build();
    }

    @Override
    public StationVertex getSourceVertex() {
        return sourceStationVertex;
    }

    @Override
    public StationVertex getTargetVertex() {
        return railroadGraph
                .getStationVertexByName(scheduleSectionEntity.getSectionEntity().getStationEntityTo().getName());
    }

    @Override
    public String toString() {
        return "Edge (" + sourceStationVertex.getName() + " -> " +
                scheduleSectionEntity.getSectionEntity().getStationEntityTo().getName() + ")";
    }
}
