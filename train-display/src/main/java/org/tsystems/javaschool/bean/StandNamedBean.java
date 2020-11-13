package org.tsystems.javaschool.bean;

import lombok.Data;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import org.omnifaces.cdi.Startup;
import org.tsystems.javaschool.dto.StandDto;
import org.tsystems.javaschool.dto.StandRowDto;
import org.tsystems.javaschool.dto.StandUpdateDto;
import org.tsystems.javaschool.dto.StationDto;
import org.tsystems.javaschool.service.StandService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Trofim Kremen
 */
@Startup
@Named
@ApplicationScoped
@Data
public class StandNamedBean implements Serializable {

    @Inject
    @Push(channel = "standUpdate")
    private PushContext context;

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

        reloadStand();
    }

    public void reloadStand() {
        standDto = standService.getStandDtoMap().get(currentStationName);
    }

    public void updateStand(StandUpdateDto updatedStandDto) {
        stationNameList.stream()
                .filter(station -> updatedStandDto.getStationName().equals(station))
                .forEach(station -> {
                    if (updatedStandDto.isStationClosed()) {
                        standDto.setClosed(true);
                        standDto.setStandRowDtoList(Collections.emptyList());
                    } else {
                        standDto.setClosed(false);
                        if (standDto.getStandRowDtoList().isEmpty()) {
                            standService.getStandDtoMap().get(station).getStandRowDtoList()
                                    .forEach(standRowDto -> {
                                        standRowDto.setCancelled(updatedStandDto.isTrainCancelled());
                                        standRowDto.setTrainNumber(updatedStandDto.getTrainNumber());
                                        standRowDto.setDepartureTime(updatedStandDto.getDepartureTime());
                                        standRowDto.setArrivalTime(updatedStandDto.getArrivalTime());
                                        standRowDto.setMinutesDelayed(updatedStandDto.getMinutesDelayed());
                                        standDto.getStandRowDtoList().add(standRowDto);
                                    });
                        } else {
                            standDto.getStandRowDtoList().stream()
                                    .filter(standRowDto -> Objects.equals(
                                            updatedStandDto.getTrainNumber(),
                                            standRowDto.getTrainNumber()))
                                    .forEach(standRowDto -> {
                                        standRowDto.setCancelled(updatedStandDto.isTrainCancelled());
                                        standRowDto.setTrainNumber(updatedStandDto.getTrainNumber());
                                        standRowDto.setDepartureTime(updatedStandDto.getDepartureTime());
                                        standRowDto.setArrivalTime(updatedStandDto.getArrivalTime());
                                        standRowDto.setMinutesDelayed(updatedStandDto.getMinutesDelayed());
                                    });
                        }
                    }
                });
        context.send("Stand updated, nice!");
    }

    public List<StandRowDto> getDepartureStandRowList() {
        List<StandRowDto> standRowDtoList = standDto.getStandRowDtoList();
        return standRowDtoList.stream()
                .filter(standRowDto -> Objects.equals(
                        standRowDto.getDepartureStationName(),
                        getCurrentStationName()
                ))
                .collect(Collectors.toList());
    }

    public List<StandRowDto> getArrivalStandRowList() {
        List<StandRowDto> standRowDtoList = standDto.getStandRowDtoList();
        return standRowDtoList.stream()
                .filter(standRowDto -> Objects.equals(
                        standRowDto.getDestinationStationName(),
                        getCurrentStationName()
                ))
                .collect(Collectors.toList());
    }

    public boolean isTrainOnTime(StandRowDto standRowDto) {
        return standRowDto.getMinutesDelayed() == 0;
    }

    public String getStationStatus(StandDto standDto) {
        if (standDto.isClosed()) {
            return "Station is closed!";
        } else return "Station is opened!";
    }

    public String getTrainStatus(StandRowDto standRowDto) {
        if (standRowDto.isCancelled()) {
            return "Train is cancelled!";
        } else {
            return (standRowDto.getMinutesDelayed() == 0) ? "On time."
                    : "Delayed by " + standRowDto.getMinutesDelayed() + " minutes.";
        }
    }

}
