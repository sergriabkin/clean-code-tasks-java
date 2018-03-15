package com.epam.engx.cleancode.functions.task6.stubs;

import com.epam.engx.cleancode.functions.task6.thirdpartyjar.CalculationHistoryService;
import com.epam.engx.cleancode.functions.task6.thirdpartyjar.History;
import com.epam.engx.cleancode.functions.task6.thirdpartyjar.Service;

import java.util.Date;
import java.util.Map;

public class CalculationHistoryServiceStub implements CalculationHistoryService {

    private HistoryStub historyStub;

    public CalculationHistoryServiceStub(Map<Date, Double> uncalculatedFees) {
        historyStub = new HistoryStub(uncalculatedFees);
    }

    @Override
    public History retrieveHistory(Service service) {
        return historyStub;
    }

    public void verifyAppliedSum(Double expectedSum){
        historyStub.verifyAppliedSum(expectedSum);
    }
}
