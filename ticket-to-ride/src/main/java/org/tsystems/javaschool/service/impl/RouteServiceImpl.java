package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsystems.javaschool.graph.path.Path;
import org.tsystems.javaschool.graph.path.impl.DepthFirstSearch;
import org.tsystems.javaschool.graph.railroad.RailroadGraph;
import org.tsystems.javaschool.graph.railroad.SectionEdge;
import org.tsystems.javaschool.graph.railroad.StationVertex;
import org.tsystems.javaschool.mapper.ScheduleSectionMapper;
import org.tsystems.javaschool.model.dto.RouteDto;
import org.tsystems.javaschool.model.dto.RoutePartDto;
import org.tsystems.javaschool.model.dto.SearchResultDto;
import org.tsystems.javaschool.model.dto.SearchRouteFormDto;
import org.tsystems.javaschool.model.entity.ScheduleSectionEntity;
import org.tsystems.javaschool.repository.ScheduleSectionRepository;
import org.tsystems.javaschool.service.RouteService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Trofim Kremen
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RailroadGraph railroadGraph;
    private final ScheduleSectionRepository scheduleSectionRepository;
    private final ScheduleSectionMapper scheduleSectionMapper;

    @Override
    @Transactional
    public SearchResultDto findRouteGroups(SearchRouteFormDto searchRouteFormDto) {
        StationVertex departureStation = railroadGraph.getStationVertexByName(searchRouteFormDto.getStationNameFrom());
        StationVertex destinationStation = railroadGraph.getStationVertexByName(searchRouteFormDto.getStationNameTo());
        LocalDate requestedRideDate = searchRouteFormDto.getRideDate();

        Collection<Path<StationVertex, SectionEdge>> discoveredPath = DepthFirstSearch
                .findAllSimplePaths(departureStation, destinationStation, ((nextEdge, currentPath) -> {
                    LocalDate requiredDate;
                    LocalTime requiredArrivalTime = null;
                    if (currentPath.isEmpty()) {
                        requiredDate = requestedRideDate;
                    } else {
                        requiredDate = currentPath.getLastEdge().getRideDate();
                        requiredArrivalTime = scheduleSectionRepository
                                .findById(currentPath.getLastEdge().getId()).getArrival();
                    }
                    return isSectionInTimeBoundaries(nextEdge, requiredArrivalTime, requiredDate);
                }));
        return SearchResultDto.builder()
                .discoveredRoutes(mapPathsToRouteGroups(discoveredPath))
                .build();
    }

    private boolean isSectionInTimeBoundaries(SectionEdge nextEdge,
                                              LocalTime requiredArrivalTime, LocalDate requiredDate) {
        if (requiredArrivalTime != null) {
            ScheduleSectionEntity nextScheduleSection = scheduleSectionRepository
                    .findById(nextEdge.getId());
            return nextScheduleSection.getDeparture().isAfter(requiredArrivalTime) &&
                    nextEdge.getRideDate().isEqual(requiredDate);
        } else return nextEdge.getRideDate().isEqual(requiredDate);
    }

    private List<RouteDto> mapPathsToRouteGroups(Collection<Path<StationVertex, SectionEdge>> discoveredPath) {
        return discoveredPath.stream()
                .map(this::mapPathToRoute)
                .collect(Collectors.toList());
    }

    private RouteDto mapPathToRoute(Path<StationVertex, SectionEdge> discoveredPath) {
        List<SectionEdge> sectionEdgeList = discoveredPath.getEdges();
        SectionEdge firstSection = sectionEdgeList.get(0);
        SectionEdge lastSection = sectionEdgeList.get(sectionEdgeList.size() - 1);
        LocalTime departureTime = scheduleSectionRepository
                .findById(sectionEdgeList.get(0).getId()).getDeparture();
        LocalTime arrivalTime = scheduleSectionRepository
                .findById(sectionEdgeList.get(sectionEdgeList.size() - 1).getId()).getArrival();
        return RouteDto.builder()
                .id(UUID.randomUUID())
                .departureTime(LocalDateTime.of(firstSection.getRideDate(), departureTime))
                .arrivalTime(LocalDateTime.of(lastSection.getRideDate(), arrivalTime))
                .ticketsAvailable(sectionEdgeList.stream()
                        .mapToInt(SectionEdge::getTicketCountAvailable)
                        .min()
                        .orElse(0))
                .routePartDtoList(groupSectionsByTrain(sectionEdgeList))
                .build();
    }

    private List<RoutePartDto> groupSectionsByTrain(List<SectionEdge> sectionEdgeList) {
        return StreamEx.of(sectionEdgeList)
                .groupRuns((sectionEdge, sectionEdge2) ->
                        Objects.equals(sectionEdge.getTrain().getId(), sectionEdge2.getTrain().getId()))
                .map(this::mapSectionGroupToRoutePart)
                .toList();
    }

    private RoutePartDto mapSectionGroupToRoutePart(List<SectionEdge> sectionGroup) {
        SectionEdge firstSection = sectionGroup.get(0);
        SectionEdge lastSection = sectionGroup.get(sectionGroup.size() - 1);
        LocalTime departureTime = scheduleSectionRepository.findById(firstSection.getId()).getDeparture();
        LocalTime arrivalTime = scheduleSectionRepository.findById(lastSection.getId()).getArrival();
        return RoutePartDto.builder()
                .departureTime(LocalDateTime.of(firstSection.getRideDate(), departureTime))
                .arrivalTime(LocalDateTime.of(lastSection.getRideDate(), arrivalTime))
                .scheduleSectionDtoList(sectionGroup.stream()
                        .map(SectionEdge::getId)
                        .map(scheduleSectionRepository::findById)
                        .map(scheduleSectionMapper::toDto)
                        .collect(Collectors.toList())
                )
                .trainId(firstSection.getTrain().getId())
                .trainName(firstSection.getTrain().getName())
                .stationDtoFrom(firstSection.getSourceVertex().toDto())
                .stationDtoTo(lastSection.getTargetVertex().toDto())
                .build();
    }

}
