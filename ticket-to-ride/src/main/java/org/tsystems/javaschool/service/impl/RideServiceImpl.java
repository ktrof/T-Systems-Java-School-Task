package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsystems.javaschool.mapper.RideMapper;
import org.tsystems.javaschool.mapper.TrainMapper;
import org.tsystems.javaschool.model.dto.ride.RideDto;
import org.tsystems.javaschool.model.dto.train.TrainDto;
import org.tsystems.javaschool.model.entity.RideEntity;
import org.tsystems.javaschool.repository.RideRepository;

import java.time.LocalDate;

/**
 * @author Trofim Kremen
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final TrainMapper trainMapper;

    @Override
    @Transactional(readOnly = true)
    public RideDto getByTrainAndRideDate(TrainDto trainDto, LocalDate rideDate) {
        RideDto rideDto = null;
        try {
            RideEntity rideEntity = rideRepository.findByTrainAndDate(trainMapper.toEntity(trainDto), rideDate);
            rideDto = rideMapper.toDto(rideEntity);
        } catch (Exception e) {
            log.error("Error getting ride");
        }
        return rideDto;
    }
}
