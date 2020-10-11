package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutePartDto implements Serializable {


    private String trainId;
    private List<ScheduleSectionDto> scheduleSectionDtoList;
    private StationDto stationDtoFrom;
    private StationDto stationDtoTo;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    public Duration getDuration() {
        return Duration.between(departureTime, arrivalTime);
    }

    public String getStationNameSequence() {
        return scheduleSectionDtoList.stream()
                .skip(1)
                .map(ScheduleSectionDto::getSectionDto)
                .map(SectionDto::getStationDtoFrom)
                .collect(Collectors.joining(", "));
    }

    public double getDistance() {
        return scheduleSectionDtoList.stream()
                .map(ScheduleSectionDto::getSectionDto)
                .map(SectionDto::getLength)
                .reduce(0d, Double::sum);
    }

}
