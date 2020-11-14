package org.tsystems.javaschool.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tsystems.javaschool.mapper.*;
import org.tsystems.javaschool.model.dto.schedulesection.ScheduleSectionDto;
import org.tsystems.javaschool.model.dto.station.StationDto;
import org.tsystems.javaschool.model.dto.train.TrainDto;
import org.tsystems.javaschool.model.entity.ScheduleSectionEntity;
import org.tsystems.javaschool.model.entity.SectionEntity;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;
import org.tsystems.javaschool.repository.ScheduleSectionRepository;
import org.tsystems.javaschool.service.ScheduleSectionService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

/**
 * @author Trofim Kremen
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class ScheduleSectionServiceImplTest {

    ScheduleSectionService scheduleSectionService;

    @Mock ScheduleSectionRepository scheduleSectionRepository;
    @Spy  StationMapper stationMapper = new StationMapperImpl();
    @Spy  TrainMapper trainMapper = new TrainMapperImpl();
    @Mock ScheduleSectionMapper scheduleSectionMapper;

    @BeforeEach
    public void init() {
        scheduleSectionService = new ScheduleSectionServiceImpl(
                scheduleSectionRepository,
                stationMapper,
                trainMapper,
                scheduleSectionMapper
        );
    }

    private ScheduleSectionEntity getTestScheduleSectionEntity() {
        return ScheduleSectionEntity.builder()
                .id(11)
                .indexWithinTrainRoute(1)
                .departure(LocalTime.now())
                .arrival(LocalTime.now())
                .stopDuration(1)
                .trainEntity(new TrainEntity())
                .sectionEntity(SectionEntity.builder()
                        .stationEntityFrom(StationEntity.builder()
                                .id(1)
                                .build())
                        .stationEntityTo(StationEntity.builder()
                                .id(2)
                                .build())
                        .build())
                .build();
    }

    private ScheduleSectionDto getTestScheduleSectionDto() {
        return ScheduleSectionDto.builder()
                .id(11)
                .indexWithinTrainRoute(1)
                .build();
    }

    @Test
    public void testGetByDepartureStationAndRideDate() {
        when(scheduleSectionRepository.findByStationAndRideDate(any(StationEntity.class), any(LocalDate.class)))
                .thenReturn(Collections.singletonList(getTestScheduleSectionEntity()));
        when(scheduleSectionMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(getTestScheduleSectionDto()));
        List<ScheduleSectionDto> result = scheduleSectionService
                .getByDepartureStationAndRideDate(StationDto.builder().id(1).build(), LocalDate.now());
        assertEquals(1, result.size());
        ScheduleSectionDto dto = result.get(0);
        assertEquals(getTestScheduleSectionEntity().getId(), dto.getId());
        assertEquals(getTestScheduleSectionEntity().getIndexWithinTrainRoute(), dto.getIndexWithinTrainRoute());
    }

    @Test
    public void testGetByArrivalStationAndRideDate() {
        when(scheduleSectionRepository.findByStationAndRideDate(any(StationEntity.class), any(LocalDate.class)))
                .thenReturn(Collections.singletonList(getTestScheduleSectionEntity()));
        when(scheduleSectionMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(getTestScheduleSectionDto()));
        List<ScheduleSectionDto> result = scheduleSectionService
                .getByDestinationStationAndRideDate(StationDto.builder().id(2).build(), LocalDate.now());
        assertEquals(1, result.size());
        ScheduleSectionDto dto = result.get(0);
        assertEquals(getTestScheduleSectionEntity().getId(), dto.getId());
        assertEquals(getTestScheduleSectionEntity().getIndexWithinTrainRoute(), dto.getIndexWithinTrainRoute());
    }

    @Test
    public void testGetByTrainAndRideDate() {
        when(scheduleSectionRepository.findByTrainAndRideDate(any(TrainEntity.class), any(LocalDate.class)))
                .thenReturn(Collections.singletonList(getTestScheduleSectionEntity()));
        when(scheduleSectionMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(getTestScheduleSectionDto()));
        List<ScheduleSectionDto> result = scheduleSectionService
                .getByTrainAndRideDate(TrainDto.builder().build(), LocalDate.now());
        assertEquals(1, result.size());
        ScheduleSectionDto dto = result.get(0);
        assertEquals(getTestScheduleSectionEntity().getId(), dto.getId());
        assertEquals(getTestScheduleSectionEntity().getIndexWithinTrainRoute(), dto.getIndexWithinTrainRoute());
    }

}
