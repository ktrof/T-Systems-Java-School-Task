package org.tsystems.javaschool.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.tsystems.javaschool.dto.StandDto;
import org.tsystems.javaschool.dto.StationDto;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
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

    @EJB
    private RestClientService restClientService;

    private LocalDate rideDate = LocalDate.now();
    private List<StationDto> stationDtoList;
    private Map<String, StandDto> standDtoMap;

    @PostConstruct
    public void init() {
        standDtoMap = new HashMap<>();
        updateStationDtoList();
        updateStandMap();
    }

    public void updateStationDtoList() {
        stationDtoList = restClientService.getStationStandList();
        if (stationDtoList.isEmpty()) {
            log.error("Error getting station list");
        } else {
            log.info("Station list updated");
        }
    }

    public void updateStandMap() {
        stationDtoList.forEach(stationDto -> standDtoMap.put(
                stationDto.getName(),
                restClientService.getStandByStationIdAndRideDate(stationDto.getId(), rideDate))
        );
        if (standDtoMap.isEmpty()) {
            log.error("Error getting stand info");
        } else {
            log.info("Stand info has been put to map");
        }
    }

}
