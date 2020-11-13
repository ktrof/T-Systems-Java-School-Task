package org.tsystems.javaschool.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tsystems.javaschool.model.dto.stand.StandDto;
import org.tsystems.javaschool.model.entity.*;
import org.tsystems.javaschool.repository.RideRepository;
import org.tsystems.javaschool.repository.RideScheduleRepository;
import org.tsystems.javaschool.repository.ScheduleSectionRepository;
import org.tsystems.javaschool.repository.StationRepository;
import org.tsystems.javaschool.service.StationStandService;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * @author Trofim Kremen
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class StationStandServiceImplTest {

    StationStandService stationStandService;

    @Mock StationRepository stationRepository;
    @Mock ScheduleSectionRepository scheduleSectionRepository;
    @Mock RideScheduleRepository rideScheduleRepository;
    @Mock RideRepository rideRepository;

    @BeforeEach
    public void init() {
        stationStandService = new StationStandServiceImpl(
                stationRepository,
                scheduleSectionRepository,
                rideScheduleRepository,
                rideRepository
        );
    }

    private StationEntity getTestStationEntity() {
        return StationEntity.builder()
                .name("test")
                .closed(false)
                .build();
    }

    private ScheduleSectionEntity getTestScheduleSectionEntity() {
        return ScheduleSectionEntity.builder()
                .trainEntity(TrainEntity.builder()
                        .id("101D")
                        .build())
                .indexWithinTrainRoute(1)
                .sectionEntity(SectionEntity.builder()
                        .id(1)
                        .stationEntityFrom(StationEntity.builder().name("test").build())
                        .stationEntityTo(StationEntity.builder().name("test2").build())
                        .build())
                .build();
    }

    private RideScheduleEntity getTestRideScheduleEntity() {
        return RideScheduleEntity.builder()
                .departure(ZonedDateTime.now())
                .arrival(ZonedDateTime.now().plusDays(1))
                .minutesDelayed(0)
                .trainEntity(TrainEntity.builder().build())
                .rideDate(LocalDate.now())
                .build();
    }

    private RideEntity getTestRideEntity() {
        return RideEntity.builder()
                .cancelled(false)
                .build();
    }

    @Test
    public void testGetByStationIdAndRideDate() {
        when(stationRepository.findById(anyInt())).thenReturn(getTestStationEntity());
        when(scheduleSectionRepository.findByStationAndRideDate(any(StationEntity.class), any(LocalDate.class)))
                .thenReturn(Collections.singletonList(getTestScheduleSectionEntity()));
        when(rideScheduleRepository.findByTrainAndSectionIndexAndArrivalDate(any(TrainEntity.class), anyInt(), any(LocalDate.class)))
                .thenReturn(getTestRideScheduleEntity());
        when(rideRepository.findByTrainAndDate(any(TrainEntity.class), any(LocalDate.class)))
                .thenReturn(getTestRideEntity());
        StandDto result = stationStandService.getByStationIdAndRideDate(1, LocalDate.now());
        assertEquals(getTestStationEntity().getName(), result.getStationName());
        assertEquals(getTestStationEntity().isClosed(), result.isClosed());
        assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), result.getRideDate());
        assertEquals(1, Collections.singletonList(getTestScheduleSectionEntity()).size());
        verify(scheduleSectionRepository, times(Collections.singletonList(getTestScheduleSectionEntity()).size() + 1))
                .findByStationAndRideDate(any(StationEntity.class), any(LocalDate.class));
    }

}
