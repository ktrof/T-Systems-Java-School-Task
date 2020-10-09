package org.tsystems.javaschool.graph.railroad.impl;

import lombok.Builder;
import org.tsystems.javaschool.graph.railroad.RailroadGraph;
import org.tsystems.javaschool.graph.railroad.SectionEdge;
import org.tsystems.javaschool.graph.railroad.StationVertex;
import org.tsystems.javaschool.model.dto.SectionDto;
import org.tsystems.javaschool.model.entity.SectionEntity;

/**
 * @author Trofim Kremen
 */
@Builder
public class DatabaseSectionEdge implements SectionEdge {

    private final RailroadGraph railroadGraph;
    private final StationVertex sourceStationVertex;
    private final SectionEntity sectionEntity;

    @Override
    public int getId() {
        return sectionEntity.getId();
    }

    @Override
    public double getLength() {
        return sectionEntity.getLength();
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
        return railroadGraph.getStationVertexByName(sectionEntity.getStationEntityTo().getName());
    }

    @Override
    public String toString() {
        return "Edge (" + sourceStationVertex.getName() + " -> " + sectionEntity.getStationEntityTo().getName() + ")";
    }
}
