package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSectionDto implements Serializable {

    private int id;

    @NotNull(message = "Set the train")
    private TrainDto trainDto;

    @NotNull(message = "Set the section id")
    private int sectionDtoId;

    @NotNull(message = "Set stop duration")
    private long stopDuration;

    @Min(value = 0, message = "Amount of tickets left can not be negative")
    @NotBlank
    private int ticketsAvailable;

    private int indexWithinTrainRoute;

    @DateTimeFormat(pattern = "HH:mm")
    private Instant arrival;

    @DateTimeFormat(pattern = "HH:mm")
    private Instant departure;

}
