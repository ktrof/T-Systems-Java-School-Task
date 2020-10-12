package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
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
    private String trainName;
    private List<ScheduleSectionDto> scheduleSectionDtoList;
    private StationDto stationDtoFrom;
    private StationDto stationDtoTo;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

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
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        return (scheduleSectionDtoList.stream()
                .map(ScheduleSectionDto::getSectionDto)
                .map(SectionDto::getLength)
                .map(decimalFormat::format)
                .map(Double::valueOf)
                .reduce(0d, Double::sum));
    }

}
