package org.tsystems.javaschool.model.dto.train;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelayFormDto implements Serializable {

    @Min(value = 0, message = "delay can not be negative")
    @Max(value = 1440, message = "delay can not be greater than one day")
    private int minutesDelayed;

}
