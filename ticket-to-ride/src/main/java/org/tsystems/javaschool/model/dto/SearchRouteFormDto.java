package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.tsystems.javaschool.constraint.ValidateFields;

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
        first = "stationNameFrom",
        second = "stationNameTo",
        message = "Stations can not match"
)
public class SearchRouteFormDto implements Serializable {

    @NotNull(message = "Set station from")
    private String stationNameFrom;

    @NotNull(message = "Set station to")
    private String stationNameTo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rideDate;

}
