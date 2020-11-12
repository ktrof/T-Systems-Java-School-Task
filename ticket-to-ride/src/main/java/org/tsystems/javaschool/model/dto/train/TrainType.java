package org.tsystems.javaschool.model.dto.train;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Getter
public enum TrainType {
    COMMON("Common"),
    PREMIUM("Premium"),
    LEGENDARY("Legendary");

    private static final Map<String, TrainType> ENUM_MAP;

    private final String typeOfString;

    static {
        Map<String, TrainType> map = new ConcurrentHashMap<>();
        Arrays.stream(TrainType.values())
                .forEach(trainType -> map.put(trainType.getTypeOfString(), trainType));
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static TrainType getTypeOfEnum(String typeOfString) {
        return ENUM_MAP.get(typeOfString);
    }

}
