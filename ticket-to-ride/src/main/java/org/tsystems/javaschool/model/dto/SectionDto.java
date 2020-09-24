package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionDto implements Serializable {

    private int id;

    @NotNull(message = "Set departure station")
    private StationDto stationDtoFrom;

    @NotNull(message = "Set arrival station")
    private StationDto stationDtoTo;

    @Min(value = 0, message = "Amount of tickets left can not be negative")
    @NotBlank
    private int ticketsAvailable;

    @Min(value = 0, message = "Price can not be negative")
    @NotBlank
    private int price;

}
