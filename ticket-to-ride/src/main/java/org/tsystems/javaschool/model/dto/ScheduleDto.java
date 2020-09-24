package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto implements Serializable {

    private int id;

    @NotNull(message = "Set the train")
    private TrainDto trainDto;

    @NotNull(message = "Set the section")
    private SectionDto sectionDto;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Instant arrival;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Instant departure;

}
