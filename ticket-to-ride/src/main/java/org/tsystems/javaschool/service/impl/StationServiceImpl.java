package org.tsystems.javaschool.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.mapper.StationMapper;
import org.tsystems.javaschool.model.dto.StationDto;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.repository.StationRepository;
import org.tsystems.javaschool.service.StationService;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Station service.
 *
 * @author Trofim Kremen
 */
@Service
@Slf4j
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private StationMapper stationMapper;


    @Override
    public List<StationDto> getAll() {
        List<StationDto> stationDtoList = null;
        try {
            List<StationEntity> stationEntityList = stationRepository.findAll();
            stationDtoList = stationMapper.toDtoList(stationEntityList);
        } catch (Exception e) {
            log.error("Error getting all the stations", e);
        }
        return stationDtoList;
    }

    @Override
    public StationDto getById(int id) {
        StationDto stationDto = null;
        try {
            stationDto = stationMapper.toDto(stationRepository.findById(id));
        } catch (Exception e) {
            log.error("Error getting the station by id", e);
        }
        return stationDto;
    }

    @Override
    public StationDto save(StationDto stationDto) {
        try {
            StationEntity existingStationEntity = stationRepository.findByName(stationDto.getName());
            if (existingStationEntity != null) {
                throw new SBBException("Station with given name already exists");
            }
            stationRepository.add(stationMapper.toEntity(stationDto));
        } catch (Exception e) {
            log.error("Error creating new station", e);
        }
        return stationDto;
    }

    @Override
    public StationDto edit(StationDto stationDto) {
        try {
            stationRepository.update(stationMapper.toEntity(stationDto));
        } catch (Exception e) {
            log.error("Error changing station name", e);
        }
        return stationDto;
    }

    @Override
    public void delete(StationDto stationDto) {
        try {
            stationRepository.remove(stationMapper.toEntity(stationDto));
        } catch (Exception e) {
            log.error("Error deleting station", e);
        }
    }

}
