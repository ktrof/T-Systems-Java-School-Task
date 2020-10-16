package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddTrainFormDto implements Serializable {

    @NotBlank(message = "Symbol code must not be blank")
    @Pattern(regexp = "^\\d{3}[A-Z]$", message = "Three digits and one capital letter are allowed")
    private String id;

    @NotBlank(message = "Train name must not be blank")
    @Pattern(regexp = "^[a-zA-Z-]+$",message = "Latin letters and dashes are allowed")
    private String name;

    @Min(value = 40, message = "Min train speed is 40 km/h")
    @Max(value = 300, message = "Max train speed is 300 km/h")
    @NotNull(message = "Set the average speed of train")
    private int avgSpeed;

    @Min(value = 1, message = "Min number of seats is 1")
    @NotNull(message = "Set the number of seats")
    private int numberOfSeats;

    @NotBlank(message = "Set departure date")
    @Pattern(regexp = "^([2][0-3]|[0-1][0-9]|[1-9]):[0-5][0-9]$", message = "pattern {HH:mm}")
    private String departure;

    private String type;

    @NotBlank(message = "Choose ride dates")
    private String dates;

    private ScheduleSectionFormDto[] scheduleSectionFormDtoArray;
}
