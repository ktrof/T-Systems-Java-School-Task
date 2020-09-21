package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZoneId;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StationDto {

    @NotNull
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private float latitude;

    @NotBlank
    private float longitude;

    @NotBlank
    private ZoneId timezone;

    private List<TrainStationDto> trains;

}
