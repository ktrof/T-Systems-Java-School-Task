package org.tsystems.javaschool.model.dto.train;

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

    private String id;
    private String name;
    private int avgSpeed;
    private int numberOfSeats;
    private TrainType type;
    private boolean cancelled;

}
