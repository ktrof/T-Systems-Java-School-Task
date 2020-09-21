package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainStationDto {

    @NotNull
    private int id;

    @NotBlank
    private TrainDto train;

    @NotBlank
    private StationDto station;

    private Instant arrival;

    @NotBlank
    private Duration waitingTime;

    private Instant departure;

}
