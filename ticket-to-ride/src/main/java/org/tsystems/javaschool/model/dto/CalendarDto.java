package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDto implements Serializable {

    private int id;

    @NotNull(message = "Set the train")
    private TrainDto trainDto;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rideDate;

}
