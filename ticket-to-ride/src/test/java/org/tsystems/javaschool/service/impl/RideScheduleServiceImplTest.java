package org.tsystems.javaschool.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tsystems.javaschool.mapper.RideScheduleMapper;
import org.tsystems.javaschool.mapper.TrainMapper;
import org.tsystems.javaschool.mapper.TrainMapperImpl;
import org.tsystems.javaschool.model.dto.rideschedule.RideScheduleDto;
import org.tsystems.javaschool.model.dto.train.TrainDto;
import org.tsystems.javaschool.model.entity.RideScheduleEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;
import org.tsystems.javaschool.repository.RideScheduleRepository;
import org.tsystems.javaschool.service.RideScheduleService;

import java.time.LocalDate;
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
public class RideScheduleServiceImplTest {

    RideScheduleService rideScheduleService;

    @Mock RideScheduleRepository rideScheduleRepository;
    @Mock RideScheduleMapper rideScheduleMapper;
    @Spy  TrainMapper trainMapper = new TrainMapperImpl();

    @BeforeEach
    public void init() {
        rideScheduleService = new RideScheduleServiceImpl(rideScheduleRepository, rideScheduleMapper, trainMapper);
    }

    private TrainEntity getTrainEntity() {
        return TrainEntity.builder()
                .id("420N")
                .avgSpeed(69)
                .name("Blue")
                .type("Sus")
                .build();
    }

    private RideScheduleEntity getTestEntity() {
        return RideScheduleEntity.builder()
                .trainEntity(getTrainEntity())
                .rideDate(LocalDate.now())
                .build();
    }

    private TrainDto getTrainDto() {
        return TrainDto.builder()
                .id("420N")
                .avgSpeed(69)
                .name("Blue")
                .build();
    }

    private RideScheduleDto getTestDto() {
        return RideScheduleDto.builder()
                .trainDto(getTrainDto())
                .rideDate(LocalDate.now())
                .build();
    }

    @Test
    public void testGetByTrainAndRideDate() {
        when(rideScheduleRepository.findByTrainAndRideDate(any(TrainEntity.class), any(LocalDate.class)))
                .thenReturn(Collections.singletonList(getTestEntity()));
        when(rideScheduleMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(getTestDto()));
        List<RideScheduleDto> result = rideScheduleService.getByTrainAndRideDate(
                TrainDto.builder().build(), LocalDate.now());
        assertEquals(1, result.size());
        RideScheduleDto dto = result.get(0);
        assertEquals(getTrainEntity(), getTestEntity().getTrainEntity());
    }
}
