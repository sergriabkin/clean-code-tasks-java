package com.epam.engx.cleancode.functions.task6.stubs;

import com.epam.engx.cleancode.functions.task6.thirdpartyjar.History;
import com.epam.engx.cleancode.functions.task6.thirdpartyjar.Service;
import com.epam.engx.cleancode.functions.task6.thirdpartyjar.Tariff;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.epam.engx.cleancode.functions.task6.stubs.DateCreator.createDate;
import static com.epam.engx.cleancode.functions.task6.UserAccountTest.UNIT_RATE;
import static org.junit.Assert.assertEquals;

public class HistoryStub implements History {

    protected static final double DELTA = 0.0001;
    Double appliedSum;

    Map<Date, Double> fees = getCalculatedFees();

    private HashMap<Date, Double> getCalculatedFees() {
        return new HashMap<Date, Double>(){{
            put(createDate(2001, 3, 28), 100.0);
            put(createDate(2001, 4, 18), 150.0);
        }};
    }

    public HistoryStub(Map<Date, Double> uncalculatedFees) {
        this.fees.putAll(uncalculatedFees);
    }

    @Override
    public Map<Date, Double> getAllFees(Tariff tariff, Service service) {
        return fees;
    }

    @Override
    public void applyRecalculation(Double value, double unitRate) {
        appliedSum = value;
        assertEquals(UNIT_RATE, unitRate, DELTA);
    }

    public void verifyAppliedSum(Double expectedSum){
        assertEquals(expectedSum, appliedSum, DELTA);
    }
}
