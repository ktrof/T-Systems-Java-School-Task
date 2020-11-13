package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tsystems.javaschool.mapper.StationMapper;
import org.tsystems.javaschool.model.dto.section.SectionDto;
import org.tsystems.javaschool.model.dto.stand.StandDto;
import org.tsystems.javaschool.model.dto.stand.StandRowDto;
import org.tsystems.javaschool.model.dto.stand.StandUpdateDto;
import org.tsystems.javaschool.model.dto.station.AddStationFormDto;
import org.tsystems.javaschool.model.dto.station.StationDto;
import org.tsystems.javaschool.model.entity.RideScheduleEntity;
import org.tsystems.javaschool.model.entity.SectionEntity;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.repository.*;
import org.tsystems.javaschool.service.StationService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Station service.
 *
 * @author Trofim Kremen
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;
    private final SectionRepository sectionRepository;
    private final ScheduleSectionRepository scheduleSectionRepository;
    private final RideRepository rideRepository;
    private final RideScheduleRepository rideScheduleRepository;
    private final StationMapper stationMapper;
    private final MessageSender messageSender;

    @Override
    @Transactional
    public List<StationDto> getAll() {
        List<StationDto> stationDtoList = null;
        try {
            List<StationEntity> stationEntityList = stationRepository.findAll();
            stationDtoList = stationMapper.toDtoList(stationEntityList);
        } catch (Exception e) {
            log.error("Error getting all the stations", e);
        }
        return stationDtoList;
    }

    @Override
    public StationDto getById(int id) {
        StationDto stationDto = null;
        try {
            stationDto = stationMapper.toDto(stationRepository.findById(id));
        } catch (Exception e) {
            log.error("Error getting the station by id", e);
        }
        return stationDto;
    }

    @Override
    @Transactional
    public StationDto getByName(String name) {
        StationDto stationDto = null;
        try {
            stationDto = stationMapper.toDto(stationRepository.findByName(name));
        } catch (Exception e) {
            log.error("Error getting station by name");
        }
        return stationDto;
    }

    @Override
    @Transactional
    public AddStationFormDto save(AddStationFormDto stationFormDto) {
        try {
            StationEntity newStationEntity = stationMapper.toEntity(stationFormDto);
            stationRepository.add(newStationEntity);

            if (stationFormDto.getCorrespondingSectionList() != null
                    && stationFormDto.getCorrespondingSectionList().length > 0) {
                try {
                    List<SectionEntity> sectionEntityList = new ArrayList<>();
                    for (SectionDto sectionDto : stationFormDto.getCorrespondingSectionList()) {

                        StationEntity stationEntityFrom = stationRepository.findByName(sectionDto.getStationDtoFrom());
                        StationEntity stationEntityTo = stationRepository.findByName(sectionDto.getStationDtoTo());

                        SectionEntity forwardSectionEntity = new SectionEntity();
                        forwardSectionEntity.setStationEntityFrom(stationEntityFrom);
                        forwardSectionEntity.setStationEntityTo(stationEntityTo);
                        forwardSectionEntity.setLength(calculateDistance(stationEntityFrom, stationEntityTo));
                        sectionEntityList.add(forwardSectionEntity);

                        SectionEntity backwardSectionEntity = new SectionEntity();
                        backwardSectionEntity.setStationEntityFrom(stationEntityTo);
                        backwardSectionEntity.setStationEntityTo(stationEntityFrom);
                        backwardSectionEntity.setLength(calculateDistance(stationEntityTo, stationEntityFrom));
                        sectionEntityList.add(backwardSectionEntity);
                    }
                    sectionRepository.add(sectionEntityList);
                } catch (Exception e) {
                    log.error("Error creating sections", e);
                }
            }
        } catch (Exception e) {
            log.error("Error creating new station", e);
        }
        return stationFormDto;
    }

    private double calculateDistance(StationEntity stationEntityFrom, StationEntity stationEntityTo) {
        double latFrom = Math.toRadians(stationEntityFrom.getLatitude());
        double lonFrom = Math.toRadians(stationEntityFrom.getLongitude());
        double latTo = Math.toRadians(stationEntityTo.getLatitude());
        double lonTo = Math.toRadians(stationEntityTo.getLongitude());

        // Haversine formula
        double deltaLat = latTo - latFrom;
        double deltaLon = lonTo - lonFrom;
        double radiusKm = 6371;
        double haversine = Math.pow(Math.sin(deltaLat / 2), 2)
                + Math.cos(latFrom) * Math.cos(latTo)
                * Math.pow(Math.sin(deltaLon / 2),2);

        return 2 * radiusKm * Math.asin(Math.sqrt(haversine));
    }

    @Override
    public StationDto edit(StationDto stationDto) {
        try {
            stationRepository.update(stationMapper.toEntity(stationDto));
        } catch (Exception e) {
            log.error("Error changing station name", e);
        }
        return stationDto;
    }

    @Override
    @Transactional
    public void close(int stationId) {
        try {
            StationEntity stationEntity = stationRepository.findById(stationId);
            stationRepository.close(stationEntity);
            messageSender.sendMessage(StandUpdateDto.builder()
                    .stationName(stationEntity.getName())
                    .isStationClosed(true)
                    .build());
        } catch (Exception e) {
            log.error("Error closing the station", e);
        }
    }

    @Override
    @Transactional
    public void open(int stationId) {
        try {
            StationEntity stationEntity = stationRepository.findById(stationId);
            stationRepository.open(stationEntity);
            scheduleSectionRepository.findByStationAndRideDate(stationEntity, LocalDate.now())
                    .forEach(scheduleSection -> {
                        RideScheduleEntity rideScheduleEntity = rideScheduleRepository
                                .findByTrainAndSectionIndexAndArrivalDate(
                                        scheduleSection.getTrainEntity(),
                                        scheduleSection.getIndexWithinTrainRoute(),
                                        LocalDate.now());
                        messageSender.sendMessage(StandUpdateDto.builder()
                                .stationName(stationEntity.getName())
                                .isStationClosed(false)
                                .trainNumber(scheduleSection.getTrainEntity().getId())
                                .isTrainCancelled(rideRepository
                                        .findByTrainAndDate(
                                                rideScheduleEntity.getTrainEntity(),
                                                rideScheduleEntity.getRideDate()
                                        )
                                        .isCancelled())
                                .departureTime(rideScheduleEntity.getDeparture()
                                        .format(DateTimeFormatter.ofPattern("HH:mm")))
                                .arrivalTime(rideScheduleEntity.getArrival()
                                        .format(DateTimeFormatter.ofPattern("HH:mm")))
                                .minutesDelayed(rideScheduleEntity.getMinutesDelayed())
                                .build());
                    });
        } catch (Exception e) {
            log.error("Error opening the station", e);
        }
    }

}
