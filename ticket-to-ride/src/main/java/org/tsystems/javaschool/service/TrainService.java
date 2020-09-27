package org.tsystems.javaschool.service;

import org.tsystems.javaschool.model.dto.TrainDto;

import java.util.List;

public interface TrainService {

    List<TrainDto> findAll();
    TrainDto findById(int id);
    int createTrain(TrainDto trainDto);
    int modifyTrain(TrainDto trainDto);
    int removeTrainById(int id);

}
