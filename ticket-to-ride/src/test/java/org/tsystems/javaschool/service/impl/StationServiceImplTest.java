package org.tsystems.javaschool.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tsystems.javaschool.mapper.StationMapper;
import org.tsystems.javaschool.mapper.StationMapperImpl;
import org.tsystems.javaschool.model.dto.section.SectionDto;
import org.tsystems.javaschool.model.dto.stand.StandUpdateDto;
import org.tsystems.javaschool.model.dto.station.AddStationFormDto;
import org.tsystems.javaschool.model.dto.station.StationDto;
import org.tsystems.javaschool.model.entity.*;
import org.tsystems.javaschool.repository.*;
import org.tsystems.javaschool.service.StationService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Trofim Kremen
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class StationServiceImplTest {

    StationService stationService;

    @Mock   StationRepository stationRepository;
    @Mock   SectionRepository sectionRepository;
    @Mock   ScheduleSectionRepository scheduleSectionRepository;
    @Mock   RideRepository rideRepository;
    @Mock   RideScheduleRepository rideScheduleRepository;
    @Mock   MessageSender messageSender;
    @Spy    StationMapper stationMapper = new StationMapperImpl();
    @Captor ArgumentCaptor<StationEntity> stationCaptor;

    @BeforeEach
    public void init() {
        stationService = new StationServiceImpl(
                stationRepository,
                sectionRepository,
                scheduleSectionRepository,
                rideRepository,
                rideScheduleRepository,
                stationMapper,
                messageSender
        );
    }

    private StationEntity getTestEntity() {
        return StationEntity.builder()
                .id(111)
                .name("test")
                .timezone(ZoneId.of("UTC"))
                .closed(true)
                .latitude(1L)
                .longitude(2L)
                .build();
    }

    private StationEntity getNewEntity() {
        return StationEntity.builder()
                .id(112)
                .name("test2")
                .timezone(ZoneId.of("UTC"))
                .closed(false)
                .latitude(2L)
                .longitude(1L)
                .build();
    }

    private List<ScheduleSectionEntity> getTestSectionList() {
        ScheduleSectionEntity scheduleSectionEntity = ScheduleSectionEntity.builder()
                .id(111)
                .trainEntity(new TrainEntity())
                .indexWithinTrainRoute(1)
                .build();
        return Collections.singletonList(scheduleSectionEntity);
    }

    private RideEntity getTestRideEntity() {
        return RideEntity.builder()
                .trainEntity(new TrainEntity())
                .rideDate(LocalDate.now())
                .cancelled(false)
                .ticketsAvailable(10)
                .build();
    }

    private RideScheduleEntity getTestRideScheduleEntity() {
        return RideScheduleEntity.builder()
                .trainEntity(new TrainEntity())
                .rideDate(LocalDate.now())
                .departure(ZonedDateTime.now())
                .arrival(ZonedDateTime.now().plusDays(1))
                .minutesDelayed(0)
                .build();
    }

    private AddStationFormDto getStationForm() {
        return AddStationFormDto.builder()
                .id(112)
                .name("test2")
                .timezone("UTC")
                .latitude(2L)
                .longitude(1L)
                .correspondingSectionList(new SectionDto[]{SectionDto.builder()
                        .stationDtoTo("test2")
                        .stationDtoFrom(getTestEntity().getName())
                        .build()})
                .build();
    }

    @Test
    public void testGetAll() {
        when(stationRepository.findAll()).thenReturn(Collections.singletonList(getTestEntity()));
        List<StationDto> result = stationService.getAll();
        assertEquals(1, result.size());
        StationDto dto = result.get(0);
        assertEquals(getTestEntity().getId(), dto.getId());
        assertEquals(getTestEntity().getName(), dto.getName());
    }

    @Test
    public void testGetById() {
        when(stationRepository.findById(anyInt())).thenReturn(getTestEntity());
        StationDto result = stationService.getById(anyInt());
        assertEquals(getTestEntity().getId(), result.getId());
        assertEquals(getTestEntity().getName(), result.getName());
    }

    @Test
    public void testGetByName() {
        when(stationRepository.findByName(anyString())).thenReturn(getTestEntity());
        StationDto result = stationService.getByName(anyString());
        assertEquals(getTestEntity().getId(), result.getId());
        assertEquals(getTestEntity().getName(), result.getName());
    }

    @Test
    public void testSave() {
        when(stationRepository.findByName(anyString())).thenReturn(getTestEntity());
        stationService.save(getStationForm());
        verify(stationRepository, atMostOnce()).add(any(StationEntity.class));
        verify(stationRepository, times(getStationForm().getCorrespondingSectionList().length * 2))
                .findByName(anyString());
        verify(sectionRepository, only()).add(anyList());
    }

    @Test
    public void testClose() {
        when(stationRepository.findById(anyInt())).thenReturn(getTestEntity());
        doNothing().when(stationRepository).close(stationCaptor.capture());
        stationService.close(anyInt());
        assertTrue(stationCaptor.getValue().isClosed());
        verify(messageSender, only()).sendMessage(any(StandUpdateDto.class));
    }

    @Test
    public void testOpen() {
        when(stationRepository.findById(anyInt())).thenReturn(getNewEntity());
        when(scheduleSectionRepository.findByStationAndRideDate(any(StationEntity.class), any(LocalDate.class)))
                .thenReturn(getTestSectionList());
        when(rideScheduleRepository.findByTrainAndSectionIndexAndArrivalDate(any(TrainEntity.class), anyInt(), any(LocalDate.class)))
                .thenReturn(getTestRideScheduleEntity());
        when(rideRepository.findByTrainAndDate(any(TrainEntity.class), any(LocalDate.class)))
                .thenReturn(getTestRideEntity());
        doNothing().when(stationRepository).open(stationCaptor.capture());
        stationService.open(anyInt());
        assertFalse(stationCaptor.getValue().isClosed());
        verify(messageSender, times(getTestSectionList().size())).sendMessage(any(StandUpdateDto.class));
        verify(rideScheduleRepository, times(getTestSectionList().size()))
                .findByTrainAndSectionIndexAndArrivalDate(any(TrainEntity.class), anyInt(), any(LocalDate.class));
        verify(rideRepository, times(getTestSectionList().size()))
                .findByTrainAndDate(any(TrainEntity.class), any(LocalDate.class));
    }

}
