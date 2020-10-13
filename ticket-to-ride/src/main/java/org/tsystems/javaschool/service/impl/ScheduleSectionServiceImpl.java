package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsystems.javaschool.mapper.ScheduleSectionMapper;
import org.tsystems.javaschool.mapper.StationMapper;
import org.tsystems.javaschool.mapper.TrainMapper;
import org.tsystems.javaschool.model.dto.ScheduleSectionDto;
import org.tsystems.javaschool.model.dto.StationDto;
import org.tsystems.javaschool.model.dto.TrainDto;
import org.tsystems.javaschool.model.entity.ScheduleSectionEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;
import org.tsystems.javaschool.repository.ScheduleSectionRepository;
import org.tsystems.javaschool.service.ScheduleSectionService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Trofim Kremen
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleSectionServiceImpl implements ScheduleSectionService {

    private final ScheduleSectionRepository scheduleSectionRepository;
    private final StationMapper stationMapper;
    private final TrainMapper trainMapper;
    private final ScheduleSectionMapper scheduleSectionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleSectionDto> getByDepartureStationAndRideDate(StationDto departureStation, LocalDate rideDate) {
        List<ScheduleSectionDto> scheduleSectionDtoList = null;
        try {
            List<ScheduleSectionEntity> scheduleSectionEntityList = scheduleSectionRepository
                    .findByDepartureStationAndRideDate(stationMapper.toEntity(departureStation), rideDate);
            scheduleSectionDtoList = scheduleSectionMapper.toDtoList(scheduleSectionEntityList);
        } catch (Exception e) {
            log.error("Error getting schedule sections by station and ride date", e);
        }
        return scheduleSectionDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleSectionDto> getByTrain(TrainDto trainDto) {
        List<ScheduleSectionDto> scheduleSectionDtoList = null;
        try {
            List<ScheduleSectionEntity> scheduleSectionEntityList = scheduleSectionRepository
                    .findByTrain(trainMapper.toEntity(trainDto));
            scheduleSectionDtoList = scheduleSectionMapper.toDtoList(scheduleSectionEntityList);
        } catch (Exception e) {
            log.error("Error getting schedule section by train" ,e);
        }
        return scheduleSectionDtoList;
    }

}
