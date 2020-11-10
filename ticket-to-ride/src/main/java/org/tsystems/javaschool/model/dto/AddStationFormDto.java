package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZoneId;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddStationFormDto implements Serializable {

    private int id;

    @NotBlank(message = "Station name must not be blank")
    @Pattern(regexp = "^[a-zA-Z-& ]+$",message = "Latin letters and dashes are allowed")
    private String name;

    @Min(value = -90, message = "Min latitude value is -90°")
    @Max(value = 90, message = "Max latitude value is 90°")
    @NotNull(message = "set latitude")
    private double latitude;

    @Min(value = -180, message = "Min longitude value is -180°")
    @Max(value = 180, message = "Max longitude value is 180°")
    @NotNull(message = "set longitude")
    private double longitude;

    @NotBlank(message = "Timezone must not be blank")
    private String timezone;

    private SectionDto[] correspondingSectionList;

}
