package org.tsystems.javaschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tsystems.javaschool.model.dto.TrainDto;
import org.tsystems.javaschool.repository.ITrainRepository;
import org.tsystems.javaschool.service.ITrainService;

import java.util.List;

@Service
public class TrainService implements ITrainService {

    @Autowired
    private ITrainRepository trainRepository;


    @Override
    public List<TrainDto> findAll() {
        return trainRepository.findAll();
    }

    @Override
    public TrainDto findById(int id) {
        return trainRepository.findById(id);
    }

    @Override
    public int createTrain(TrainDto trainDto) {
        return trainRepository.createTrain(trainDto);
    }

    @Override
    public int modifyTrain(TrainDto trainDto) {
        return trainRepository.modifyTrain(trainDto);
    }

    @Override
    public int removeTrainById(int id) {
        return trainRepository.removeTrainById(id);
    }
}
