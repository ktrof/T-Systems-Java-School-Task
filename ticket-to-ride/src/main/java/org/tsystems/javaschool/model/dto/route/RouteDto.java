package org.tsystems.javaschool.model.dto.route;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tsystems.javaschool.model.dto.station.StationDto;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteDto implements Serializable {

    private UUID id;
    private List<RoutePartDto> routePartDtoList;
    private ZonedDateTime departureTime;
    private ZonedDateTime arrivalTime;
    private int ticketsAvailable;

    public StationDto getDepartureStation() {
        return routePartDtoList.get(0).getStationDtoFrom();
    }

    public StationDto getArrivalStation() {
        return routePartDtoList.get(routePartDtoList.size() - 1).getStationDtoTo();
    }

    public Duration getTotalDuration() {
        return Duration.between(departureTime, arrivalTime);
    }

    public double getTotalDistance() {
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        double totalDistance = routePartDtoList.stream()
                .map(RoutePartDto::getDistance)
                .reduce(0d, Double::sum);
        return Double.parseDouble(decimalFormat.format(totalDistance));
    }

    public double getTotalPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        double totalPrice = routePartDtoList.stream()
                .map(RoutePartDto::getPrice)
                .reduce(0d, Double::sum);
        return Double.parseDouble(decimalFormat.format(totalPrice));
    }

    private int getTransferCount() {
        return routePartDtoList.size() - 1;
    }
}
