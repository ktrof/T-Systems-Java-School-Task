package org.tsystems.javaschool.model.dto.route;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tsystems.javaschool.model.dto.schedulesection.ScheduleSectionDto;
import org.tsystems.javaschool.model.dto.section.SectionDto;
import org.tsystems.javaschool.model.dto.station.StationDto;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
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
    private ZonedDateTime departureTime;
    private ZonedDateTime arrivalTime;

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
        double distance = scheduleSectionDtoList.stream()
                .map(ScheduleSectionDto::getSectionDto)
                .map(SectionDto::getLength)
                .reduce(0d, Double::sum);
        return Double.parseDouble(decimalFormat.format(distance));
    }

}
