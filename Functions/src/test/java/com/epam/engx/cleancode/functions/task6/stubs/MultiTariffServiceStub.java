package com.epam.engx.cleancode.functions.task6.stubs;

import com.epam.engx.cleancode.functions.task6.thirdpartyjar.Service;
import com.epam.engx.cleancode.functions.task6.thirdpartyjar.Tariff;

import java.util.List;

public class MultiTariffServiceStub implements Service {

    private final List<Tariff> tariffs;

    public MultiTariffServiceStub(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    @Override
    public List<Tariff> getTariffs() {
        return tariffs;
    }
}
