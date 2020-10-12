package org.tsystems.javaschool.graph.railroad.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tsystems.javaschool.graph.railroad.RailroadGraph;
import org.tsystems.javaschool.graph.railroad.StationVertex;
import org.tsystems.javaschool.model.entity.ScheduleSectionEntity;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.repository.ScheduleSectionRepository;
import org.tsystems.javaschool.repository.StationRepository;
import org.tsystems.javaschool.repository.TicketScheduleSectionRepository;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Trofim Kremen
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DatabaseRailroadGraph implements RailroadGraph {

    private final ConcurrentHashMap<String, StationVertex> stationVertexMap = new ConcurrentHashMap<>();

    private final ScheduleSectionRepository scheduleSectionRepository;
    private final StationRepository stationRepository;
    private final TicketScheduleSectionRepository ticketScheduleSectionRepository;

    @Override
    public StationVertex getStationVertexByName(String name) {
        StationVertex stationVertex = null;
        try {
            stationVertex = stationVertexMap.computeIfAbsent(name, stationName -> {
                StationEntity stationEntity = stationRepository.findByName(stationName);
                return new DatabaseStationVertex(stationEntity.getId(), this, stationEntity, scheduleSectionRepository);
            });
        } catch (Exception e) {
            log.error("Error getting station by name", e);
        }
        return stationVertex;
    }

    @Override
    public int countBoughtTickets(ScheduleSectionEntity scheduleSectionEntity, LocalDate rideDate) {
        return ticketScheduleSectionRepository
                .findByScheduleSectionIdAndDepartureDate(scheduleSectionEntity.getId(), rideDate).size();
    }


}
