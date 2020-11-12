package org.tsystems.javaschool.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tsystems.javaschool.mapper.TrainMapper;
import org.tsystems.javaschool.mapper.TrainMapperImpl;
import org.tsystems.javaschool.model.dto.schedulesection.ScheduleSectionFormDto;
import org.tsystems.javaschool.model.dto.train.AddTrainFormDto;
import org.tsystems.javaschool.model.dto.train.DelayFormDto;
import org.tsystems.javaschool.model.dto.train.TrainDto;
import org.tsystems.javaschool.model.dto.train.TrainType;
import org.tsystems.javaschool.model.entity.*;
import org.tsystems.javaschool.repository.*;
import org.tsystems.javaschool.service.TrainService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author Trofim Kremen
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class TrainServiceImplTest {

    TrainService trainService;

    @Mock TrainRepository trainRepository;
    @Mock RideRepository rideRepository;
    @Mock ScheduleSectionRepository scheduleSectionRepository;
    @Mock RideScheduleRepository rideScheduleRepository;
    @Mock SectionRepository sectionRepository;
    @Mock StationRepository stationRepository;
    @Spy  TrainMapper trainMapper = new TrainMapperImpl();

    @BeforeEach
    public void init() {
        trainService = new TrainServiceImpl(
                trainRepository,
                rideRepository,
                scheduleSectionRepository,
                rideScheduleRepository,
                sectionRepository,
                stationRepository,
                trainMapper,
                null
        );
    }

    private TrainEntity getTestTrainEntity() {
        return TrainEntity.builder()
                .id("420G")
                .avgSpeed(69)
                .name("Blue")
                .type("Common")
                .build();
    }

    private SectionEntity getTestSectionEntity() {
        return SectionEntity.builder()
                .id(111)
                .length(111)
                .build();
    }

    private ScheduleSectionEntity getTestScheduleSectionEntity() {
        return ScheduleSectionEntity.builder()
                .arrival(LocalTime.now())
                .indexWithinTrainRoute(1)
                .build();
    }

    private RideEntity getTestRideEntity() {
        return RideEntity.builder().build();
    }

    private RideScheduleEntity getTestRideScheduleEntity() {
        return RideScheduleEntity.builder()
                .trainEntity(getTestTrainEntity())
                .minutesDelayed(0)
                .departure(ZonedDateTime.now())
                .arrival(ZonedDateTime.now().plusDays(1))
                .rideDate(LocalDate.now())
                .departureDatePlan(LocalDate.now())
                .departureDateFact(LocalDate.now())
                .arrivalDatePlan(LocalDate.now().plusDays(1))
                .arrivalDateFact(LocalDate.now().plusDays(1))
                .indexWithinTrainRoute(1)
                .build();
    }

    private AddTrainFormDto getTrainForm() {
        return AddTrainFormDto.builder()
                .id("419O")
                .name("Orange")
                .dates("2020-11-11,2020-11-12")
                .departure("18:21")
                .avgSpeed(69)
                .numberOfSeats(20)
                .type(TrainType.COMMON)
                .scheduleSectionFormDtoArray(new ScheduleSectionFormDto[]{
                        ScheduleSectionFormDto.builder()
                                .indexWithinTrainRoute(1)
                                .sectionDtoId(111)
                                .stopDuration(2)
                                .build()
                })
                .build();
    }

    private DelayFormDto getDelayForm() {
        return DelayFormDto.builder()
                .indexWithinTrainRoute(1)
                .arrivalDate(LocalDate.now().plusDays(1))
                .minutesDelayed(10)
                .build();
    }

    @Test
    public void testGetAll() {
        when(trainRepository.findAll()).thenReturn(Collections.singletonList(getTestTrainEntity()));
        List<TrainDto> result = trainService.getAll();
        assertEquals(1, result.size());
        TrainDto dto = result.get(0);
        assertEquals(getTestTrainEntity().getId(), dto.getId());
        assertEquals(getTestTrainEntity().getName(), dto.getName());
        assertEquals(getTestTrainEntity().getAvgSpeed(), dto.getAvgSpeed());
    }

    @Test
    public void testGetById() {
        when(trainRepository.findById(anyString())).thenReturn(getTestTrainEntity());
        TrainDto result = trainService.getById(anyString());
        assertEquals(getTestTrainEntity().getId(), result.getId());
        assertEquals(getTestTrainEntity().getName(), result.getName());
    }

    @Test
    public void testSave() {
        when(sectionRepository.findById(anyInt())).thenReturn(getTestSectionEntity());
        when(sectionRepository.findByScheduleSectionId(anyInt())).thenReturn(getTestSectionEntity());
        trainService.save(getTrainForm());
        verify(trainRepository, only()).add(any(TrainEntity.class));
        verify(rideRepository, only()).add(anyList());
        verify(sectionRepository, times(getTrainForm().getScheduleSectionFormDtoArray().length * 2))
                .findById(anyInt());
        verify(scheduleSectionRepository, atMost(1)).add(anyList());
        verify(stationRepository, only()).findAllByTrain(any(TrainEntity.class));
        verify(rideScheduleRepository, only()).add(anyList());
    }

    @Test
    public void testCancelTrain() {
        when(trainRepository.findById(anyString())).thenReturn(getTestTrainEntity());
        when(rideRepository.findAllByTrain(any(TrainEntity.class))).thenReturn(Collections.singletonList(getTestRideEntity()));
        trainService.cancelTrain(getTestTrainEntity().getId());
        verify(rideRepository, atMostOnce()).cancelAllRides(anyCollection());
    }

    @Test
    public void testCancelRide() {
        when(trainRepository.findById(anyString())).thenReturn(getTestTrainEntity());
        when(rideRepository.findByTrainAndDate(any(TrainEntity.class), any(LocalDate.class)))
                .thenReturn(getTestRideEntity());
        trainService.cancelRide(getTestTrainEntity().getId(), getTestRideEntity().getRideDate());
        verify(rideRepository, atMostOnce()).cancelRide(any(RideEntity.class));
    }

    @Test
    public void testRestartTrain() {
        when(trainRepository.findById(anyString())).thenReturn(getTestTrainEntity());
        when(rideRepository.findAllByTrain(any(TrainEntity.class))).thenReturn(Collections.singletonList(getTestRideEntity()));
        trainService.restartTrain(getTestTrainEntity().getId());
        verify(rideRepository, atMostOnce()).restartAllRides(anyCollection());
    }

    @Test
    public void testRestartRide() {
        when(trainRepository.findById(anyString())).thenReturn(getTestTrainEntity());
        when(rideRepository.findByTrainAndDate(any(TrainEntity.class), any(LocalDate.class)))
                .thenReturn(getTestRideEntity());
        trainService.restartRide(getTestTrainEntity().getId(), getTestRideEntity().getRideDate());
        verify(rideRepository, atMostOnce()).restartRide(any(RideEntity.class));
    }

//    TODO:
//    @Test
//    public void testDelayTrain() {
//        when(trainRepository.findById(anyString())).thenReturn(getTestTrainEntity());
//        when(rideScheduleRepository.findByTrainAndSectionIndexAndArrivalDate(any(TrainEntity.class), anyInt(), any(LocalDate.class)))
//                .thenReturn(getTestRideScheduleEntity());
//        when(scheduleSectionRepository.findByTrainAndSectionIndex(any(TrainEntity.class), anyInt()))
//                .thenReturn(getTestScheduleSectionEntity());
//        when(rideScheduleRepository.findByTrainAndRideDate(any(TrainEntity.class), any(LocalDate.class)))
//                .thenReturn(Collections.singletonList(getTestRideScheduleEntity()));
//        trainService.delayTrain(getTestTrainEntity().getId(), getDelayForm());
//        verify(rideScheduleRepository, times(Collections.singletonList(getTestRideScheduleEntity()).size()))
//                .delayArrival(any(RideScheduleEntity.class), any(ZonedDateTime.class), anyInt());
//        verify(rideScheduleRepository, times(Collections.singletonList(getTestRideScheduleEntity()).size()))
//                .delayDeparture(any(RideScheduleEntity.class), any(ZonedDateTime.class), anyInt());
//    }

}
