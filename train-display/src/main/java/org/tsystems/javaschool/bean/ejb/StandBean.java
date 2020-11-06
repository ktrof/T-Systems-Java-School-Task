package org.tsystems.javaschool.bean.ejb;

import lombok.Getter;
import lombok.Setter;
import org.tsystems.javaschool.dto.StandDto;
import org.tsystems.javaschool.dto.StandRowDto;
import org.tsystems.javaschool.dto.StandUpdateDto;
import org.tsystems.javaschool.dto.StationDto;
import org.tsystems.javaschool.service.StandService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Trofim Kremen
 */
@Startup
@SessionScoped
@Getter
@Setter
public class StandBean implements Serializable {

    @EJB
    private StandService standService;

    @EJB
    private RecieverConfigBean recieverConfigBean;

    private List<String> stationNameList;
    private String currentStationName;
    private StandDto standDto;

    @PostConstruct
    public void init() {
        recieverConfigBean.openConnection();
        Optional<StationDto> currentStationOptional = standService.getStationDtoList().stream()
                .findAny();
        currentStationOptional.ifPresent(stationDto -> currentStationName = stationDto.getName());

        stationNameList = standService.getStationDtoList().stream()
                .map(StationDto::getName)
                .collect(Collectors.toList());

        standDto = standService.getStandDtoMap().get(currentStationName);
    }


    public void updateStand(StandUpdateDto updatedStandDto) {
        if (updatedStandDto.getStationName().equals(currentStationName)) {
            if (updatedStandDto.isStationClosed()) {
                standDto.setClosed(true);
                standDto.setStandRowDtoList(Collections.emptyList());
            } else {
                standDto.setClosed(false);
                if (standDto.getStandRowDtoList().isEmpty()) {
                    standService.getStandDtoMap().get(currentStationName).getStandRowDtoList()
                            .forEach(standRowDto -> {
                                updateStandRow(standRowDto, updatedStandDto);
                                standDto.getStandRowDtoList().add(standRowDto);
                            });
                } else {
                    standDto.getStandRowDtoList().stream()
                            .filter(standRowDto -> Objects.equals(
                                    updatedStandDto.getTrainNumber(),
                                    standRowDto.getTrainNumber()))
                            .forEach(standRowDto -> updateStandRow(standRowDto, updatedStandDto));
                }
            }
        }
    }

    private void updateStandRow(StandRowDto standRowDto, StandUpdateDto updatedStandDto) {
        standRowDto.setCancelled(updatedStandDto.isTrainCancelled());
        standRowDto.setTrainNumber(updatedStandDto.getTrainNumber());
        standRowDto.setDepartureTime(updatedStandDto.getDepartureTime());
        standRowDto.setArrivalTime(updatedStandDto.getArrivalTime());
        standRowDto.setMinutesDelayed(updatedStandDto.getMinutesDelayed());
        standRowDto.setCancelled(updatedStandDto.isTrainCancelled());
    }

    public StandDto getStandRowDtoListByStation(String currentStationName) {
        return standService.getStandDtoMap().get(currentStationName);
    }

}
