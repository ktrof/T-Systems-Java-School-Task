package org.tsystems.javaschool.util;

import org.tsystems.javaschool.model.dto.schedulesection.ScheduleSectionDto;
import org.tsystems.javaschool.model.dto.train.TrainType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Trofim Kremen
 */
public final class PriceCalculator {

    private static final Map<TrainType, Function<Double, Double>> TRAIN_TYPE_FUNCTION_MAP = new HashMap<>();

    static {
        TRAIN_TYPE_FUNCTION_MAP.put(TrainType.COMMON, TrainTypePriceComputer::computeCommonPrice);
        TRAIN_TYPE_FUNCTION_MAP.put(TrainType.PREMIUM, TrainTypePriceComputer::computePremiumPrice);
        TRAIN_TYPE_FUNCTION_MAP.put(TrainType.LEGENDARY, TrainTypePriceComputer::computeLegendaryPrice);
    }

    public static double computePrice(ScheduleSectionDto scheduleSectionDto) {
        TrainType trainType = scheduleSectionDto.getTrainDto().getType();
        double length = scheduleSectionDto.getSectionDto().getLength();

        Function<Double, Double> function = TRAIN_TYPE_FUNCTION_MAP.get(trainType);

        if (function == null) {
            throw new IllegalArgumentException("Illegal train type " + trainType.getTypeOfString());
        }

        return function.apply(length);
    }

}
