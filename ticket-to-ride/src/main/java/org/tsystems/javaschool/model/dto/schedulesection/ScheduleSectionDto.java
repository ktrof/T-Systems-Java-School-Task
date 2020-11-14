package org.tsystems.javaschool.model.dto.schedulesection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tsystems.javaschool.model.dto.section.SectionDto;
import org.tsystems.javaschool.model.dto.train.TrainDto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSectionDto implements Serializable {

    private int id;

    @NotNull(message = "Set the train")
    private TrainDto trainDto;

    @NotNull(message = "Set the section")
    private SectionDto sectionDto;

    @NotNull(message = "Set stop duration")
    private long stopDuration;

    private int indexWithinTrainRoute;

    private LocalTime departure;

    private LocalTime arrival;

}
