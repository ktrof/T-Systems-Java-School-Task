package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainDto implements Serializable {

    private int id;

    @NotBlank(message = "Symbol code must not be blank")
    @Pattern(regexp = "^\\d{3}[A-Z]$", message = "Three digits and one capital letter are allowed")
    private String symbolCode;

    @NotBlank(message = "Train name must not be blank")
    @Pattern(regexp = "^[a-zA-Z-]+$",message = "Latin letters and dashes are allowed")
    private String name;

    @Min(value = 40, message = "Min train speed is 40 km/h")
    @Max(value = 300, message = "Max train speed is 300 km/h")
    @NotBlank(message = "Set the average speed of train")
    private int avgSpeed;

    @Min(value = 8, message = "Min number of seats is 8")
    @NotBlank(message = "Set the number of seats")
    private int numberOfSeats;

    @NotBlank(message = "Set the train type")
    private String type;

}
