package com.epam.engx.cleancode.functions.task6.thirdpartyjar;

import java.util.Date;
import java.util.Map;

public interface History {

    Map<Date,Double> getAllFees(Tariff tariff, Service service);

    void applyRecalculation(Double value, double unitRate);

}
