package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tsystems.javaschool.mapper.SectionMapper;
import org.tsystems.javaschool.model.dto.SectionDto;
import org.tsystems.javaschool.repository.SectionRepository;
import org.tsystems.javaschool.service.SectionService;

import java.util.List;

/**
 * @author Trofim Kremen
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final SectionMapper sectionMapper;

    @Override
    public List<SectionDto> getAll() {
        List<SectionDto> sectionDtoList = null;
        try {
            sectionDtoList = sectionMapper.toDtoList(sectionRepository.findAll());
        } catch (Exception e) {
            log.error("Error getting all the sections", e);
        }
        return sectionDtoList;
    }
}
