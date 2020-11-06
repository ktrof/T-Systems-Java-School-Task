package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsystems.javaschool.mapper.TrainMapper;
import org.tsystems.javaschool.mapper.RideScheduleMapper;
import org.tsystems.javaschool.model.dto.rideschedule.RideScheduleDto;
import org.tsystems.javaschool.model.dto.schedulesection.ScheduleSectionFormDto;
import org.tsystems.javaschool.model.dto.stand.StandDto;
import org.tsystems.javaschool.model.dto.stand.StandRowDto;
import org.tsystems.javaschool.model.dto.stand.StandUpdateDto;
import org.tsystems.javaschool.model.dto.train.AddTrainFormDto;
import org.tsystems.javaschool.model.dto.train.TrainDto;
import org.tsystems.javaschool.model.entity.*;
import org.tsystems.javaschool.repository.*;
import org.tsystems.javaschool.service.TrainService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
    private final RideRepository rideRepository;
    private final ScheduleSectionRepository scheduleSectionRepository;
    private final RideScheduleRepository rideScheduleRepository;
    private final SectionRepository sectionRepository;
    private final StationRepository stationRepository;
    private final TrainMapper trainMapper;
    private final RideScheduleMapper rideScheduleMapper;
    private final MessageSender messageSender;

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
                List<RideEntity> rideEntityList = new ArrayList<>();
                for (LocalDate rideDate : rideDates) {
                    RideEntity rideEntity = new RideEntity();
                    rideEntity.setTrainEntity(trainEntity);
                    rideEntity.setRideDate(rideDate);
                    rideEntity.setTicketsAvailable(trainEntity.getNumberOfSeats());

                    rideEntityList.add(rideEntity);
                }
                rideRepository.add(rideEntityList);
            } catch (Exception e) {
                log.error("Error creating ride dates", e);
            }

            ScheduleSectionFormDto[] scheduleSectionForms = trainFormDto.getScheduleSectionFormDtoArray();
            if (scheduleSectionForms.length != 0) {
                try {
                    List<ScheduleSectionEntity> scheduleSectionEntityList = new ArrayList<>();
                    for (int i = 0; i < scheduleSectionForms.length; i++) {
                        ScheduleSectionEntity scheduleSectionEntity = new ScheduleSectionEntity();
                        scheduleSectionEntity.setTrainEntity(trainEntity);
                        scheduleSectionEntity.setSectionEntity(sectionRepository
                                .findById(scheduleSectionForms[i].getSectionDtoId()));
                        scheduleSectionEntity.setIndexWithinTrainRoute(scheduleSectionForms[i]
                                .getIndexWithinTrainRoute());
                        scheduleSectionEntity.setStopDuration(scheduleSectionForms[i].getStopDuration());
                        if (i == 0) {
                            scheduleSectionEntity.setDeparture(
                                    LocalTime.parse(trainFormDto
                                            .getDeparture(), DateTimeFormatter.ofPattern("HH:mm"))
                            );
                        }
                        else {
                            scheduleSectionEntity.setDeparture(
                                    scheduleSectionEntityList.get(i-1).getArrival()
                                            .plusMinutes(scheduleSectionEntityList.get(i-1).getStopDuration())
                            );
                        }
                        scheduleSectionEntity.setArrival(
                                scheduleSectionEntity.getDeparture()
                                        .plusMinutes(timeBetweenTwoStations(trainEntity, sectionRepository
                                                .findById(scheduleSectionForms[i].getSectionDtoId())))
                        );
                        scheduleSectionEntityList.add(scheduleSectionEntity);
                    }
                scheduleSectionRepository.add(scheduleSectionEntityList);
                } catch (Exception e) {
                    log.error("Error creating schedule sections", e);
                }
            }
        } catch (Exception e) {
            log.error("Error creating a train", e);
        }
        return trainFormDto;
    }

    private long timeBetweenTwoStations(TrainEntity trainEntity, SectionEntity sectionEntity) {
        return Double.valueOf(60 * sectionEntity.getLength() / trainEntity.getAvgSpeed()).longValue();
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

    @Override
    public void cancelTrain(TrainDto trainDto) {
        try {
            TrainEntity trainEntity = trainMapper.toEntity(trainDto);
            rideRepository.cancelAllRides(rideRepository.findAllByTrain(trainEntity));
            sendMessage(trainEntity);
        } catch (Exception e) {
            log.error("Error canceling the train", e);
        }
    }

    @Override
    public void cancelRide(TrainDto trainDto, LocalDate rideDate) {
        try {
            TrainEntity trainEntity = trainMapper.toEntity(trainDto);
            rideRepository.cancelRide(rideRepository.findByTrainAndDate(trainEntity, rideDate));
            sendMessage(trainEntity);
        } catch (Exception e) {
            log.error("Error cancelling the ride", e);
        }
    }

    @Override
    public void restartTrain(TrainDto trainDto) {
        try {
            TrainEntity trainEntity = trainMapper.toEntity(trainDto);
            rideRepository.restartAllRides(rideRepository.findAllByTrain(trainEntity));
            sendMessage(trainEntity);
        } catch (Exception e) {
            log.error("Error restarting the train", e);
        }
    }

    @Override
    public void restartRide(TrainDto trainDto, LocalDate rideDate) {
        try {
            TrainEntity trainEntity = trainMapper.toEntity(trainDto);
            rideRepository.restartRide(rideRepository.findByTrainAndDate(trainEntity, rideDate));
            sendMessage(trainEntity);
        } catch (Exception e) {
            log.error("Error restarting the ride", e);
        }
    }

    @Override
    public void delayTrain(String trainId, RideScheduleDto rideScheduleDto, int minutesDelayed) {
        try {
            TrainEntity trainEntity = trainRepository.findById(trainId);
            RideScheduleEntity rideScheduleEntity = rideScheduleMapper.toEntity(rideScheduleDto);
            ScheduleSectionEntity scheduleSectionEntity = scheduleSectionRepository.findByTrainAndSectionIndex(trainEntity,
                    rideScheduleEntity.getIndexWithinTrainRoute());

            //delay first section
            rideScheduleRepository.delayArrival(rideScheduleEntity,
                    ZonedDateTime.of(
                            rideScheduleEntity.getArrivalDatePlan(),
                            scheduleSectionEntity.getArrival(),
                            rideScheduleEntity.getArrival().getZone()).plusMinutes(minutesDelayed),
                    minutesDelayed);
            //delay rest sections
            rideScheduleRepository.findByTrainAndRideDate(trainEntity, LocalDate.now()).stream()
                    .skip(rideScheduleEntity.getIndexWithinTrainRoute())
                    .forEach(rideSchedule -> {
                        ScheduleSectionEntity scheduleSectionEntity1 = scheduleSectionRepository
                                .findByTrainAndSectionIndex(trainEntity, rideSchedule.getIndexWithinTrainRoute());
                        rideScheduleRepository.delayDeparture(rideSchedule,
                                ZonedDateTime.of(
                                        rideSchedule.getDepartureDatePlan(),
                                        scheduleSectionEntity1.getDeparture(),
                                        rideSchedule.getDeparture().getZone()).plusMinutes(minutesDelayed),
                                minutesDelayed);
                        rideScheduleRepository.delayArrival(rideSchedule,
                                ZonedDateTime.of(
                                        rideSchedule.getArrivalDatePlan(),
                                        scheduleSectionEntity1.getArrival(),
                                        rideSchedule.getArrival().getZone()).plusMinutes(minutesDelayed),
                                minutesDelayed);
                    });
            sendMessage(trainEntity);
        } catch (Exception e) {
            log.error("Error delaying the train", e);
        }
    }

    private void sendMessage(TrainEntity trainEntity) {
        List<StationEntity> stationEntityList = stationRepository.findAllByTrain(trainEntity);
        stationEntityList
                .forEach(stationEntity -> scheduleSectionRepository.findByStationAndRideDate(stationEntity, LocalDate.now())
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
                                            .findByTrainAndDate(scheduleSection.getTrainEntity(), LocalDate.now())
                                            .isCancelled())
                                    .departureTime(rideScheduleEntity.getDeparture())
                                    .arrivalTime(rideScheduleEntity.getArrival())
                                    .minutesDelayed(rideScheduleEntity.getMinutesDelayed())
                                    .build());
                        })
                );
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
