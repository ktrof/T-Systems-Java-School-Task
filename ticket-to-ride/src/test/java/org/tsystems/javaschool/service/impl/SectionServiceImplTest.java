package org.tsystems.javaschool.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tsystems.javaschool.mapper.SectionMapper;
import org.tsystems.javaschool.mapper.StationMapper;
import org.tsystems.javaschool.model.dto.section.SectionDto;
import org.tsystems.javaschool.model.entity.SectionEntity;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.repository.SectionRepository;
import org.tsystems.javaschool.service.SectionService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

/**
 * @author Trofim Kremen
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class SectionServiceImplTest {

    SectionService sectionService;

    @Mock SectionRepository sectionRepository;
    @Mock SectionMapper sectionMapper;

    @BeforeEach
    public void init() {
        sectionService = new SectionServiceImpl(sectionRepository, sectionMapper);
    }

    private SectionEntity getTestSectionEntity() {
        return SectionEntity.builder()
                .id(1)
                .length(2)
                .stationEntityFrom(StationEntity.builder()
                        .id(1)
                        .name("test")
                        .build())
                .stationEntityTo(StationEntity.builder()
                        .id(2)
                        .name("test2")
                        .build())
                .build();
    }

    private SectionDto getTestSectionDto() {
        return SectionDto.builder()
                .id(1)
                .length(2)
                .stationDtoFrom("test")
                .stationDtoTo("test2")
                .build();
    }

    @Test
    public void testGetAll() {
        when(sectionRepository.findAll()).thenReturn(Collections.singletonList(getTestSectionEntity()));
        when(sectionMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(getTestSectionDto()));
        List<SectionDto> result = sectionService.getAll();
        assertEquals(1, result.size());
        SectionDto dto = result.get(0);
        assertEquals(getTestSectionEntity().getId(), dto.getId());
        assertEquals(getTestSectionEntity().getLength(), dto.getLength());
        assertEquals(getTestSectionEntity().getStationEntityFrom().getName(), dto.getStationDtoFrom());
        assertEquals(getTestSectionEntity().getStationEntityTo().getName(), dto.getStationDtoTo());
    }

}
