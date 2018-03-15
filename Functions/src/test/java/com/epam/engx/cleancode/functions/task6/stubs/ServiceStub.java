package com.epam.engx.cleancode.functions.task6.stubs;

import com.epam.engx.cleancode.functions.task6.thirdpartyjar.Service;
import com.epam.engx.cleancode.functions.task6.thirdpartyjar.Tariff;

import java.util.Collections;
import java.util.List;

public class ServiceStub implements Service {
    @Override
    public List<Tariff> getTariffs() {
        return Collections.<Tariff>singletonList(new UnitBasedTariffStub());
    }

}
