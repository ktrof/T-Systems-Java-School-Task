package org.tsystems.javaschool.model.dto.schedulesection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Trofim Kremen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSectionFormDto implements Serializable {

    @NotNull(message = "Set the section id")
    private int sectionDtoId;

    @NotNull(message = "Set stop duration")
    private long stopDuration;

    @NotNull(message = "index can not be null")
    private int indexWithinTrainRoute;

}
