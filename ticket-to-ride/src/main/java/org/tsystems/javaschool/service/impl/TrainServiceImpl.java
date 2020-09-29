package org.tsystems.javaschool.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.mapper.TrainMapper;
import org.tsystems.javaschool.model.dto.TrainDto;
import org.tsystems.javaschool.model.entity.TrainEntity;
import org.tsystems.javaschool.repository.TrainRepository;
import org.tsystems.javaschool.service.TrainService;

import java.util.List;

/**
 * The type Train service.
 *
 * @author Trofim Kremen
 */
@Service
@Slf4j
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private TrainMapper trainMapper;

    @Override
    public List<TrainDto> getAll() {
        List<TrainDto> trainDtoList  = null;
        trainDtoList = trainMapper.toDtoList(trainRepository.findAll());
        try {

        } catch (Exception e) {
            log.error("Error getting all the trains");
        }
        return trainDtoList;
    }

    @Override
    public TrainDto getById(String id) {
        TrainDto trainDto = null;
        try {
            trainDto = trainMapper.toDto(trainRepository.findById(id));
        } catch (Exception e) {
            log.error("Error getting train by id");
        }
        return trainDto;
    }

    @Override
    public TrainDto save(TrainDto trainDto) {
        try {
            TrainEntity existingTrainEntity = trainRepository.findById(trainDto.getId());
            if (existingTrainEntity != null) {
                throw new SBBException("Train with given id already exists");
            }
            trainRepository.add(trainMapper.toEntity(trainDto));
        } catch (Exception e) {
            log.error("Error creating a train");
        }
        return trainDto;
    }

    @Override
    public TrainDto edit(TrainDto trainDto) {
        try {
            trainRepository.update(trainMapper.toEntity(trainDto));
        } catch (Exception e) {
            log.error("Error modifying the train");
        }
        return trainDto;
    }
}
