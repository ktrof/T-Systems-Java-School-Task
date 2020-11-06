package org.tsystems.javaschool.bean.jsf;

import org.tsystems.javaschool.bean.ejb.StandBean;
import org.tsystems.javaschool.dto.StandDto;
import org.tsystems.javaschool.dto.StandRowDto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Trofim Kremen
 */
@Named
@RequestScoped
public class StandJSFModel implements Serializable {

    @Inject
    private StandBean standBean;

    public StandDto getStandDto() {
        return standBean.getStandDto();
    }

    public String getCurrentStationName() {
        return standBean.getCurrentStationName();
    }

    public List<String> getStationNameList() {
        return standBean.getStationNameList();
    }

    public List<StandRowDto> getStandRowDtoList() {
        return standBean.getStandDto().getStandRowDtoList();
    }

    public StandDto changeStand() {
        return standBean.getStandRowDtoListByStation(getCurrentStationName());
    }

    public boolean isTrainOnTime(StandRowDto standRowDto) {
        return standRowDto.getMinutesDelayed() == 0;
    }

    public List<StandRowDto> getDepartureStandRowList() {
        List<StandRowDto> standRowDtoList = getStandRowDtoList();
        return standRowDtoList.stream()
                .filter(standRowDto -> Objects.equals(
                        standRowDto.getDepartureStationName(),
                        getCurrentStationName()
                ))
                .collect(Collectors.toList());
    }

    public List<StandRowDto> getArrivalStandRowList() {
        List<StandRowDto> standRowDtoList = getStandRowDtoList();
        return standRowDtoList.stream()
                .filter(standRowDto -> Objects.equals(
                        standRowDto.getDestinationStationName(),
                        getCurrentStationName()
                ))
                .collect(Collectors.toList());
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

    public String formatDate(ZonedDateTime dateTime) {
        return DateTimeFormatter.ofPattern("dd/MM hh:mm").format(dateTime);
    }

}
