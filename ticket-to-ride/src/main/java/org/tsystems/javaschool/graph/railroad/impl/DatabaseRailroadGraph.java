package org.tsystems.javaschool.graph.railroad.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tsystems.javaschool.graph.railroad.RailroadGraph;
import org.tsystems.javaschool.graph.railroad.StationVertex;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.repository.StationRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Trofim Kremen
 */
@Component
public class DatabaseRailroadGraph implements RailroadGraph {

    private final Map<String, StationVertex> stationVertexMap = new HashMap<>();

    @Autowired
    private StationRepository stationRepository;

    @Override
    public StationVertex getStationVertexByName(String name) {
        return stationVertexMap.computeIfAbsent(name, stationName -> {
            StationEntity stationEntity = stationRepository.findByName(stationName);
            return new DatabaseStationVertex(stationEntity.getId(), this, stationEntity);
        });
    }
}
