package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.tsystems.javaschool.constraint.ValidateFields;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidateFields(
        equality = false,
        first = "stationNameFrom",
        second = "stationNameTo",
        message = "Stations can not match"
)
public class SearchRouteFormDto implements Serializable {

    @NotBlank(message = "Set departure")
    private String stationNameFrom;

    @NotBlank(message = "Set destination")
    private String stationNameTo;

    @FutureOrPresent(message = "You can not set past date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rideDate;

}
