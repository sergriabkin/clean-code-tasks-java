package com.epam.engx.cleancode.functions.task6.stubs;


import com.epam.engx.cleancode.functions.task6.thirdpartyjar.CalculationHistoryService;
import com.epam.engx.cleancode.functions.task6.thirdpartyjar.History;
import com.epam.engx.cleancode.functions.task6.thirdpartyjar.Service;

import java.util.*;

public class CalculationHistoryForMultiServiceStub implements CalculationHistoryService {

    private Map<Class<? extends Service>, HistoryStub> historyMap = new HashMap<>();

    public CalculationHistoryForMultiServiceStub(Map<Date, Double> uncalculatedFees, Map<Date, Double> secondUncalculatedFees) {
        historyMap.put(ServiceStub.class, new HistoryStub(uncalculatedFees));
        historyMap.put(SecondServiceStub.class, new HistoryStub(secondUncalculatedFees));
    }

    @Override
    public History retrieveHistory(Service service) {
        return historyMap.get(service.getClass());
    }

    public void verifyAppliedSumForService(Double expectedSum, Class<? extends Service> serviceClass) {
        historyMap.get(serviceClass).verifyAppliedSum(expectedSum);
    }
}
