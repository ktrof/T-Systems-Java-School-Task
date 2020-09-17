package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainDto {

    @NotNull
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private int tonnage;

    @NotBlank
    private int technicalSpeed;

}
