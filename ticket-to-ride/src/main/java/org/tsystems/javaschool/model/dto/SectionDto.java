package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tsystems.javaschool.constraint.ValidateFields;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidateFields(
        equality = false,
        first = "stationDtoTo",
        second = "stationDtoFrom",
        message = "Departure and destination stations must not match"
)
public class SectionDto implements Serializable {

    private int id;

    @NotNull(message = "Set departure station")
    private String stationDtoFrom;

    private String stationDtoTo;

    @Min(value = 1, message = "Distance between two stations can not less then 1")
    private double length;

}
