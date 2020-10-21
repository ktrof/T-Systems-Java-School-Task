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

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Route service.
 *
 * @author Trofim Kremen
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    public static final long MIN_ALLOWED_TIME_TO_BUY_TICKET = 10;

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
                .findAllSimplePaths(departureStation, destinationStation, ((nextEdge, currentPath) ->
                        isSearchResultWithinConditions(nextEdge, currentPath, requestedRideDate)));

        return SearchResultDto.builder()
                .discoveredRoutes(mapPathsToRouteGroups(discoveredPath))
                .build();
    }

    private boolean isSearchResultWithinConditions(SectionEdge nextEdge,
                                                   Path<StationVertex, SectionEdge> currentPath,
                                                   LocalDate requestedRideDate) {
        LocalDate requiredDate;
        LocalTime requiredDepartureTime;
        LocalTime requiredArrivalTime = null;
        ZoneId timezone;
        if (currentPath.isEmpty()) {
            requiredDate = requestedRideDate;
            requiredDepartureTime = scheduleSectionRepository.findById(nextEdge.getId()).getDeparture();
            timezone = currentPath.getSourceVertex().getTimezone();
        } else {
            requiredDate = currentPath.getLastEdge().getRideDate();
            requiredDepartureTime = scheduleSectionRepository
                    .findById(currentPath.getLastEdge().getId()).getDeparture();
            requiredArrivalTime = scheduleSectionRepository
                    .findById(currentPath.getLastEdge().getId()).getArrival();
            timezone = nextEdge.getSourceVertex().getTimezone();
        }
        return isSectionInTimeBoundaries(nextEdge, requiredArrivalTime, requiredDate)
                && areTicketsAvailable(nextEdge)
                && isTimeLeftToBuyTicket(requiredDepartureTime, requiredDate, timezone);
    }

    private boolean isSectionInTimeBoundaries(SectionEdge nextEdge, LocalTime requiredArrivalTime, LocalDate requiredDate) {
        if (requiredArrivalTime != null) {
            ScheduleSectionEntity nextScheduleSection = scheduleSectionRepository
                    .findById(nextEdge.getId());
            return nextScheduleSection.getDeparture().isAfter(requiredArrivalTime)
                    && nextEdge.getRideDate().isEqual(requiredDate);
        } else return nextEdge.getRideDate().isEqual(requiredDate);
    }

    private boolean isTimeLeftToBuyTicket(LocalTime requiredDepartureTime,
                                          LocalDate requiredDate, ZoneId timezone) {
        ZonedDateTime minTimeLeft = ZonedDateTime.now(ZoneId.of("UTC+3")).plusMinutes(MIN_ALLOWED_TIME_TO_BUY_TICKET);
        ZonedDateTime departureTime = ZonedDateTime.of(LocalDateTime.of(requiredDate, requiredDepartureTime), timezone);
        return departureTime.isAfter(minTimeLeft);
    }

    private boolean areTicketsAvailable(SectionEdge nextEdge) {
        return nextEdge.getTicketCountAvailable() > 0;
    }

    private List<RouteDto> mapPathsToRouteGroups(Collection<Path<StationVertex, SectionEdge>> discoveredPath) {
        return discoveredPath.stream()
                .map(this::mapPathToRoute)
                .sorted(Comparator.comparing(RouteDto::getTotalDuration))
                .collect(Collectors.toList());
    }

    private RouteDto mapPathToRoute(Path<StationVertex, SectionEdge> discoveredPath) {
        List<SectionEdge> sectionEdgeList = discoveredPath.getEdges();
        SectionEdge firstSection = sectionEdgeList.get(0);
        SectionEdge lastSection = sectionEdgeList.get(sectionEdgeList.size() - 1);
        ScheduleSectionEntity firstSectionEntity = scheduleSectionRepository.findById(firstSection.getId());
        ScheduleSectionEntity lastSectionEntity = scheduleSectionRepository.findById(lastSection.getId());
        LocalTime departureTime = firstSectionEntity.getDeparture();
        LocalTime arrivalTime = lastSectionEntity.getArrival();
        return RouteDto.builder()
                .id(UUID.randomUUID())
                .departureTime(ZonedDateTime.of(LocalDateTime.of(firstSection.getRideDate(), departureTime),
                        firstSectionEntity.getSectionEntity().getStationEntityFrom().getTimezone()))
                .arrivalTime(ZonedDateTime.of(LocalDateTime.of(lastSection.getRideDate(), arrivalTime),
                        lastSectionEntity.getSectionEntity().getStationEntityTo().getTimezone()))
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
        ScheduleSectionEntity firstSectionEntity = scheduleSectionRepository.findById(firstSection.getId());
        ScheduleSectionEntity lastSectionEntity = scheduleSectionRepository.findById(lastSection.getId());
        LocalTime departureTime = firstSectionEntity.getDeparture();
        LocalTime arrivalTime = lastSectionEntity.getArrival();
        return RoutePartDto.builder()
                .departureTime(ZonedDateTime.of(LocalDateTime.of(firstSection.getRideDate(), departureTime),
                        firstSectionEntity.getSectionEntity().getStationEntityFrom().getTimezone()))
                .arrivalTime(ZonedDateTime.of(LocalDateTime.of(lastSection.getRideDate(), arrivalTime),
                        lastSectionEntity.getSectionEntity().getStationEntityTo().getTimezone()))
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
