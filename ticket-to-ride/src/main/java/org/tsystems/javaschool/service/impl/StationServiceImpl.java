package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsystems.javaschool.mapper.StationMapper;
import org.tsystems.javaschool.model.dto.*;
import org.tsystems.javaschool.model.entity.SectionEntity;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;
import org.tsystems.javaschool.model.entity.TrainScheduleEntity;
import org.tsystems.javaschool.repository.*;
import org.tsystems.javaschool.service.StationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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
    private final CalendarRepository calendarRepository;
    private final TrainScheduleRepository trainScheduleRepository;
    private final StationMapper stationMapper;
    private final MessageService messageService;

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

            if (stationFormDto.getCorrespondingSectionList().length != 0) {
                try {
                    List<SectionEntity> sectionEntityList = new ArrayList<>();
                    for (SectionDto sectionDto : stationFormDto.getCorrespondingSectionList()) {

                        StationEntity stationEntityFrom = stationRepository.findByName(sectionDto.getStationDtoFrom());
                        StationEntity stationEntityTo = stationRepository.findByName(sectionDto.getStationDtoTo());

                        SectionEntity sectionEntity = new SectionEntity();
                        sectionEntity.setStationEntityFrom(stationEntityFrom);
                        sectionEntity.setStationEntityTo(stationEntityTo);
                        sectionEntity.setLength(calculateDistance(stationEntityFrom, stationEntityTo));

                        sectionEntityList.add(sectionEntity);
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
    public void close(StationDto stationDto) {
        try {
            StationEntity stationEntity = stationMapper.toEntity(stationDto);
            stationRepository.close(stationEntity);
            messageService.sendMessage(StandDto.builder()
                    .stationName(stationEntity.getName())
                    .rideDate(LocalDate.now())
                    .stationStatus("Station is closed!")
                    .build());
        } catch (Exception e) {
            log.error("Error closing the station", e);
        }
    }

    @Override
    public void open(StationDto stationDto) {
        try {
            StationEntity stationEntity = stationMapper.toEntity(stationDto);
            stationRepository.open(stationEntity);
            messageService.sendMessage(StandDto.builder()
                    .stationName(stationEntity.getName())
                    .rideDate(LocalDate.now())
                    .stationStatus("Station is opened!")
                    .standRowDtoList(scheduleSectionRepository.findByStationAndRideDate(stationEntity, LocalDate.now())
                            .stream()
                            .map(scheduleSectionEntity -> {
                                TrainScheduleEntity trainScheduleEntity = trainScheduleRepository
                                        .findByTrainAndSectionIndexAndArrivalDate(scheduleSectionEntity.getTrainEntity(),
                                                scheduleSectionEntity.getIndexWithinTrainRoute(), LocalDate.now());
                                return StandRowDto.builder()
                                        .trainNumber(scheduleSectionEntity.getTrainEntity().getId())
                                        .trainStatus(defineTrainStatus(trainScheduleEntity))
                                        .departureTime(ZonedDateTime.of(LocalDateTime.of(
                                                trainScheduleEntity.getDepartureDate(),
                                                trainScheduleEntity.getDepartureTime()
                                        ), scheduleSectionEntity.getSectionEntity().getStationEntityFrom().getTimezone()))
                                        .arrivalTime(ZonedDateTime.of(LocalDateTime.of(
                                                trainScheduleEntity.getArrivalDate(),
                                                trainScheduleEntity.getArrivalTime()
                                        ), scheduleSectionEntity.getSectionEntity().getStationEntityTo().getTimezone())
                                                .plusMinutes(trainScheduleEntity.getMinutesDelayed()))
                                        .build();
                            })
                            .collect(Collectors.toList()))
                    .build());
        } catch (Exception e) {
            log.error("Error opening the station", e);
        }
    }

    private String defineTrainStatus(TrainScheduleEntity trainScheduleEntity) {
        if (calendarRepository.findByTrainAndDate(trainScheduleEntity.getTrainEntity(),
                trainScheduleEntity.getArrivalDate()).isCancelled()) {
            return "Cancelled!";
        } else {
            return (trainScheduleEntity.getMinutesDelayed() == 0) ? "On time."
                    : "Delayed by " + trainScheduleEntity.getMinutesDelayed() + " minutes.";
        }
    }

}
