package org.tsystems.javaschool.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tsystems.javaschool.mapper.StationMapper;
import org.tsystems.javaschool.mapper.StationMapperImpl;
import org.tsystems.javaschool.model.dto.station.StationDto;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.repository.StationRepository;

import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Trofim Kremen
 */
public class StationServiceImplTest {
     StationServiceImpl stationService;

    @BeforeEach
    public void init() {
        StationRepository stationRepository = mock(StationRepository.class);
        StationMapper stationMapper = spy(new StationMapperImpl());

        StationEntity stationEntity=StationEntity.builder()
                .id(111)
                .name("test")
                .timezone(ZoneId.of("UTC"))
                .closed(true)
                .latitude(1L)
                .longitude(2L)
                .build();
        stationService = new StationServiceImpl(stationRepository,null,null,
                null,null,stationMapper,null);

         when(stationRepository.findAll()).thenReturn(Collections.singletonList(stationEntity));
    }

    @Test
    public void test() {
        List<StationDto> result=stationService.getAll();
        assertEquals(result.size(),1);
        StationDto dto=result.get(0);
        assertEquals(dto.getId(),111);
        assertEquals(dto.getName(),"test");
    }

}
