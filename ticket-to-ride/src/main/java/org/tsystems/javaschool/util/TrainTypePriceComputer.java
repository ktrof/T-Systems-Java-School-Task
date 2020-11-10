package org.tsystems.javaschool.util;

/**
 * @author Trofim Kremen
 */
public final class TrainTypePriceComputer {

    private static final double COMMON_COEFFICIENT = 0.1;
    private static final double PREMIUM_COEFFICIENT = 0.2;
    private static final double LEGENDARY_COEFFICIENT = 0.5;

    public static Double computeCommonPrice(double distance) {
        return COMMON_COEFFICIENT * distance;
    }

    public static Double computePremiumPrice(double distance) {
        return PREMIUM_COEFFICIENT * distance;
    }

    public static Double computeLegendaryPrice(double distance) {
        return LEGENDARY_COEFFICIENT * distance;
    }

}
