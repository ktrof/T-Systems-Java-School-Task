package org.tsystems.javaschool.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.tsystems.javaschool.dto.StandDto;
import org.tsystems.javaschool.dto.StationDto;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Trofim Kremen
 */
@Singleton
@Getter
@Setter
@Slf4j
public class StandService {

    @Inject
    private RestClientService restClientService;

    private LocalDate rideDate = LocalDate.now();
    private List<StationDto> stationDtoList;
    private Map<String, StandDto> standDtoMap;

    @PostConstruct
    public void init() {
        standDtoMap = new HashMap<>();
        updateStationDtoList();
        updateStandInfo();
    }

    public void updateStationDtoList() {
        stationDtoList = restClientService.getStationStandList();
        if (stationDtoList.isEmpty()) {
            log.error("Error getting station list");
        } else {
            log.info("Station list updated");
        }
    }

    public void updateStandInfo() {
        stationDtoList.forEach(stationDto -> standDtoMap.put(
                stationDto.getName(),
                restClientService.getStandByStationIdAndRideDate(stationDto.getId(), rideDate))
        );
    }

}
