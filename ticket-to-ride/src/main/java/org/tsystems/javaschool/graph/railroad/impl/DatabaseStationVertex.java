package org.tsystems.javaschool.graph.railroad.impl;

import lombok.RequiredArgsConstructor;
import org.tsystems.javaschool.graph.railroad.RailroadGraph;
import org.tsystems.javaschool.graph.railroad.SectionEdge;
import org.tsystems.javaschool.graph.railroad.StationVertex;
import org.tsystems.javaschool.model.dto.StationDto;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.repository.ScheduleSectionRepository;

import java.time.ZoneId;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Trofim Kremen
 */
@RequiredArgsConstructor
public class DatabaseStationVertex implements StationVertex {

    private final int id;
    private final RailroadGraph railroadGraph;
    private final StationEntity stationEntity;
    private final ScheduleSectionRepository scheduleSectionRepository;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return stationEntity.getName();
    }

    @Override
    public double getLatitude() {
        return stationEntity.getLatitude();
    }

    @Override
    public double getLongitude() {
        return stationEntity.getLongitude();
    }

    @Override
    public ZoneId getTimezone() {
        return stationEntity.getTimezone();
    }

    @Override
    public StationDto toDto() {
        return StationDto.builder()
                .id(getId())
                .name(getName())
                .latitude(getLatitude())
                .longitude(getLongitude())
                .timezone(getTimezone().getId())
                .build();
    }

    @Override
    public Collection<SectionEdge> getAllOutgoingEdges() {
        return stationEntity.getSectionEntityListFrom().stream()
                .map(scheduleSectionRepository::findBySection)
                .flatMap(Collection::stream)
                .flatMap(scheduleSectionEntity -> scheduleSectionEntity.getTrainEntity().getRideEntityList().stream()
                        .map(calendarEntity -> DatabaseSectionEdge.builder()
                                .railroadGraph(railroadGraph)
                                .sourceStationVertex(this)
                                .scheduleSectionEntity(scheduleSectionEntity)
                                .rideEntity(calendarEntity)
                                .build()))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Vertex (" + stationEntity.getName() + ")";
    }
}
