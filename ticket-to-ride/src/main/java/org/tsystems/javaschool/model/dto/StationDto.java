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
import java.time.ZoneId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StationDto implements Serializable {

    private int id;

    @NotBlank(message = "Station name must not be blank")
    @Pattern(regexp = "^[a-zA-Z-]+$",message = "Latin letters and dashes are allowed")
    private String name;

    @Min(value = -90, message = "Min latitude value is -90°")
    @Max(value = 90, message = "Max latitude value is 90°")
    @NotBlank
    private float latitude;

    @Min(value = -180, message = "Min longitude value is -180°")
    @Max(value = 180, message = "Max longitude value is 180°")
    @NotBlank
    private float longitude;

    @NotBlank(message = "Timezone must not be blank")
    private ZoneId timezone;

}
