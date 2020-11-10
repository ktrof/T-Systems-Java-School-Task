package org.tsystems.javaschool.model.dto.train;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TrainType {
    COMMON("Common"),
    PREMIUM("Premium"),
    LEGENDARY("Legendary");

    private final String typeOfString;

}
