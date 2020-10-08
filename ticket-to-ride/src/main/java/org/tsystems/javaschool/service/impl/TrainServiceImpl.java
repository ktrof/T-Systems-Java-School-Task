package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.mapper.TrainMapper;
import org.tsystems.javaschool.model.dto.AddTrainFormDto;
import org.tsystems.javaschool.model.dto.ScheduleSectionDto;
import org.tsystems.javaschool.model.dto.TrainDto;
import org.tsystems.javaschool.model.entity.CalendarEntity;
import org.tsystems.javaschool.model.entity.ScheduleSectionEntity;
import org.tsystems.javaschool.model.entity.SectionEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;
import org.tsystems.javaschool.repository.CalendarRepository;
import org.tsystems.javaschool.repository.ScheduleSectionRepository;
import org.tsystems.javaschool.repository.SectionRepository;
import org.tsystems.javaschool.repository.TrainRepository;
import org.tsystems.javaschool.service.TrainService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Train service.
 *
 * @author Trofim Kremen
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final CalendarRepository calendarRepository;
    private final ScheduleSectionRepository scheduleSectionRepository;
    private final SectionRepository sectionRepository;
    private final TrainMapper trainMapper;

    @Override
    @Transactional
    public List<TrainDto> getAll() {
        List<TrainDto> trainDtoList  = null;
        try {
            trainDtoList = trainMapper.toDtoList(trainRepository.findAll());
        } catch (Exception e) {
            log.error("Error getting all the trains", e);
        }
        return trainDtoList;
    }

    @Override
    @Transactional
    public TrainDto getById(String id) {
        TrainDto trainDto = null;
        try {
            trainDto = trainMapper.toDto(trainRepository.findById(id));
        } catch (Exception e) {
            log.error("Error getting train by id", e);
        }
        return trainDto;
    }

    @Override
    @Transactional
    public AddTrainFormDto save(AddTrainFormDto trainFormDto) {
        try {
            TrainEntity trainEntity = trainRepository.add(trainMapper.toEntity(trainFormDto));

            List<LocalDate> rideDates = Arrays
                    .stream(trainFormDto.getDates().split(","))
                    .map(date -> LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .collect(Collectors.toList());

            try {
                List<CalendarEntity> calendarEntityList = new ArrayList<>();
                for (LocalDate rideDate : rideDates) {
                    CalendarEntity calendarEntity = new CalendarEntity();
                    calendarEntity.setTrainEntity(trainEntity);
                    calendarEntity.setRideDate(rideDate);

                    calendarEntityList.add(calendarEntity);
                }
                calendarRepository.add(calendarEntityList);
            } catch (Exception e) {
                log.error("Error creating ride dates", e);
            }

            ScheduleSectionDto[] scheduleSectionDtos = trainFormDto.getScheduleSectionDtoArray();
            if (scheduleSectionDtos.length != 0) {
                try {
                    List<ScheduleSectionEntity> scheduleSectionEntityList = new ArrayList<>();
                    for (int i = 0; i < scheduleSectionDtos.length; i++) {
                        ScheduleSectionEntity scheduleSectionEntity = new ScheduleSectionEntity();
                        scheduleSectionEntity.setTrainEntity(trainEntity);
                        scheduleSectionEntity.setSectionEntity(sectionRepository
                                .findById(scheduleSectionDtos[i].getSectionDtoId()));
                        scheduleSectionEntity.setIndexWithinTrainRoute(scheduleSectionDtos[i]
                                .getIndexWithinTrainRoute());
                        scheduleSectionEntity.setStopDuration(scheduleSectionDtos[i].getStopDuration());
                        scheduleSectionEntity.setTicketsAvailable(trainFormDto.getNumberOfSeats());
                        if (i == 0) {
                            scheduleSectionEntity.setDeparture(
                                    LocalDateTime.parse(
                                            trainFormDto.getDeparture(), DateTimeFormatter.ofPattern("HH:mm"))
                                            .atZone(sectionRepository
                                                    .findById(scheduleSectionDtos[i].getSectionDtoId())
                                                    .getStationEntityFrom().getTimezone())
                                            .toInstant()
                            );
                        } else {
                            scheduleSectionEntity.setDeparture(
                                    scheduleSectionDtos[i - 1].getArrival()
                                            .plus(scheduleSectionDtos[i - 1].getStopDuration(), ChronoUnit.MINUTES)
                            );
                        }
                        scheduleSectionEntity.setArrival(
                                scheduleSectionEntity.getDeparture()
                                        .plus(
                                                timeBetweenTwoStations(trainEntity, sectionRepository
                                                        .findById(scheduleSectionDtos[i].getSectionDtoId())),
                                                ChronoUnit.MINUTES
                                        )
                        );

                        scheduleSectionEntityList.add(scheduleSectionEntity);
                    }
                scheduleSectionRepository.add(scheduleSectionEntityList);
                } catch (Exception e) {
                    log.error("Error creating schedule sections");
                }
            }
        } catch (Exception e) {
            log.error("Error creating a train", e);
        }
        return trainFormDto;
    }

    private long timeBetweenTwoStations(TrainEntity trainEntity, SectionEntity sectionEntity) {
        return Double.valueOf(sectionEntity.getLength() / trainEntity.getAvgSpeed() * 60).longValue();
    }

    @Override
    public TrainDto edit(TrainDto trainDto) {
        try {
            trainRepository.update(trainMapper.toEntity(trainDto));
        } catch (Exception e) {
            log.error("Error modifying the train", e);
        }
        return trainDto;
    }
}
