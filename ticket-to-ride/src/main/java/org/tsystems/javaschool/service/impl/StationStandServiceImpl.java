package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsystems.javaschool.model.dto.stand.StandDto;
import org.tsystems.javaschool.model.dto.stand.StandRowDto;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.model.entity.RideScheduleEntity;
import org.tsystems.javaschool.repository.RideRepository;
import org.tsystems.javaschool.repository.ScheduleSectionRepository;
import org.tsystems.javaschool.repository.StationRepository;
import org.tsystems.javaschool.repository.RideScheduleRepository;
import org.tsystems.javaschool.service.StationStandService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @Transactional
    public StandDto getByStationIdAndRideDate(int stationId, LocalDate rideDate) {
        StandDto standDto = null;
        try {
            StationEntity departureStationEntity = stationRepository.findById(stationId);
            standDto = StandDto.builder()
                    .stationName(departureStationEntity.getName())
                    .isClosed(departureStationEntity.isClosed())
                    .rideDate(rideDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .standRowDtoList(scheduleSectionRepository
                            .findByStationAndRideDate(departureStationEntity, rideDate).stream()
                            .map(scheduleSectionEntity -> {
                                RideScheduleEntity rideScheduleEntity = rideScheduleRepository
                                        .findByTrainAndSectionIndexAndArrivalDate(scheduleSectionEntity.getTrainEntity(),
                                                scheduleSectionEntity.getIndexWithinTrainRoute(), rideDate);
                                return StandRowDto.builder()
                                        .trainNumber(scheduleSectionEntity.getTrainEntity().getId())
                                        .departureTime(rideScheduleEntity.getDeparture()
                                                .format(DateTimeFormatter.ofPattern("HH:mm")))
                                        .departureStationName(scheduleSectionEntity.getSectionEntity()
                                                .getStationEntityFrom().getName())
                                        .arrivalTime(rideScheduleEntity.getArrival()
                                                .format(DateTimeFormatter.ofPattern("HH:mm")))
                                        .destinationStationName(scheduleSectionEntity.getSectionEntity()
                                                .getStationEntityTo().getName())
                                        .minutesDelayed(rideScheduleEntity.getMinutesDelayed())
                                        .isCancelled(rideRepository.findByTrainAndDate(rideScheduleEntity.getTrainEntity(),
                                                rideScheduleEntity.getRideDate()).isCancelled())
                                        .build();
                            })
                            .collect(Collectors.toList())
                    )
                    .build();
            System.out.println(scheduleSectionRepository.findByStationAndRideDate(departureStationEntity, rideDate));
        } catch (Exception e) {
            log.error("Error getting station stand by departure station", e);
        }
        return standDto;
    }

}
