package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainDto {

    @NotNull
    private int id;

    @NotBlank
    private String symbolCode;

    @NotBlank
    private String name;

    @NotBlank
    private int avgSpeed;

    @NotBlank
    private int numberOfSeats;

    private List<TrainStationDto> stations;

}
