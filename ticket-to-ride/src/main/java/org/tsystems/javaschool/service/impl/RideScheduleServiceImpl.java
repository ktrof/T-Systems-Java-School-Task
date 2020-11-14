package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsystems.javaschool.mapper.RideScheduleMapper;
import org.tsystems.javaschool.mapper.TrainMapper;
import org.tsystems.javaschool.model.dto.rideschedule.RideScheduleDto;
import org.tsystems.javaschool.model.dto.train.TrainDto;
import org.tsystems.javaschool.model.entity.RideScheduleEntity;
import org.tsystems.javaschool.repository.RideScheduleRepository;
import org.tsystems.javaschool.service.RideScheduleService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Trofim Kremen
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RideScheduleServiceImpl implements RideScheduleService {

    private final RideScheduleRepository rideScheduleRepository;
    private final RideScheduleMapper rideScheduleMapper;
    private final TrainMapper trainMapper;


    @Override
    @Transactional
    public List<RideScheduleDto> getByTrainAndRideDate(TrainDto trainDto, LocalDate rideDate) {
        List<RideScheduleDto> rideScheduleDtoList = new ArrayList<>();
        try {
            List<RideScheduleEntity> rideScheduleEntityList = rideScheduleRepository
                    .findByTrainAndRideDate(trainMapper.toEntity(trainDto), rideDate);
            rideScheduleDtoList = rideScheduleMapper.toDtoList(rideScheduleEntityList);
        } catch (Exception e) {
            log.error("Error getting ride schedule by train and ride date", e);
        }
        return rideScheduleDtoList;
    }

}
