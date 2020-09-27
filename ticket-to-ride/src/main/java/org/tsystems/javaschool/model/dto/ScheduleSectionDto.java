package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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

    @NotNull(message = "Set the section")
    private SectionDto sectionDto;

    @Min(value = 0, message = "Amount of tickets left can not be negative")
    @NotBlank
    private int ticketsAvailable;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Instant arrival;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Instant departure;

}
