package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tsystems.javaschool.model.dto.StandDto;
import org.tsystems.javaschool.model.dto.StandRowDto;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.model.entity.RideScheduleEntity;
import org.tsystems.javaschool.repository.RideRepository;
import org.tsystems.javaschool.repository.ScheduleSectionRepository;
import org.tsystems.javaschool.repository.StationRepository;
import org.tsystems.javaschool.repository.RideScheduleRepository;
import org.tsystems.javaschool.service.StationStandService;

import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * @author Trofim Kremen
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StationStandServiceImpl implements StationStandService {

    private final StationRepository stationRepository;
    private final ScheduleSectionRepository scheduleSectionRepository;
    private final RideScheduleRepository rideScheduleRepository;
    private final RideRepository rideRepository;

    @Override
    public StandDto getByStationIdAndRideDate(int stationId, LocalDate rideDate) {
        StandDto standDto = null;
        try {
            StationEntity departureStationEntity = stationRepository.findById(stationId);
            standDto = StandDto.builder()
                    .stationName(departureStationEntity.getName())
                    .stationStatus((departureStationEntity.isClosed()) ? "Station is closed!" : "Station is opened!")
                    .rideDate(rideDate)
                    .standRowDtoList(scheduleSectionRepository
                            .findByStationAndRideDate(departureStationEntity, rideDate).stream()
                            .map(scheduleSectionEntity -> {
                                RideScheduleEntity rideScheduleEntity = rideScheduleRepository
                                        .findByTrainAndSectionIndexAndArrivalDate(scheduleSectionEntity.getTrainEntity(),
                                                scheduleSectionEntity.getIndexWithinTrainRoute(), rideDate);
                                return StandRowDto.builder()
                                        .trainNumber(scheduleSectionEntity.getTrainEntity().getId())
                                        .departureTime(rideScheduleEntity.getDeparture())
                                        .departureStationName(scheduleSectionEntity.getSectionEntity()
                                                .getStationEntityFrom().getName())
                                        .arrivalTime(rideScheduleEntity.getArrival())
                                        .destinationStationName(scheduleSectionEntity.getSectionEntity()
                                                .getStationEntityTo().getName())
                                        .trainStatus(defineTrainStatus(rideScheduleEntity))
                                        .build();
                            })
                            .collect(Collectors.toList())
                    )
                    .build();
        } catch (Exception e) {
            log.error("Error getting station stand by departure station", e);
        }
        return standDto;
    }

    private String defineTrainStatus(RideScheduleEntity rideScheduleEntity) {
        if (rideRepository.findByTrainAndDate(rideScheduleEntity.getTrainEntity(),
                rideScheduleEntity.getRideDate()).isCancelled()) {
            return "Cancelled!";
        } else {
            return (rideScheduleEntity.getMinutesDelayed() == 0) ? "On time."
                    : "Delayed by " + rideScheduleEntity.getMinutesDelayed() + " minutes.";
        }
    }
}
