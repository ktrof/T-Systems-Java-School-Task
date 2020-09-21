package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.model.dto.TrainDto;
import org.tsystems.javaschool.model.dto.TrainStationDto;

import java.util.List;

public interface ITrainRepository {

    List<TrainDto> findAll();
    TrainDto findById(int id);
    int createTrain(TrainDto trainDto);
    int modifyTrain(TrainDto trainDto);
    int removeTrainById(int id);
    List<TrainStationDto> findAllStationsByTrainId(int id);

}
