package com.epam.engx.cleancode.functions.task6.stubs;

import com.epam.engx.cleancode.functions.task6.thirdpartyjar.Tariff;
import com.epam.engx.cleancode.functions.task6.thirdpartyjar.TariffType;

public class UnitBasedTariffStub implements Tariff {

    private double additionalFee;

    public UnitBasedTariffStub() {
        additionalFee = 0;
    }


    public UnitBasedTariffStub(double additionalFee) {
        this.additionalFee = additionalFee;
    }

    @Override
    public double getAdditionalFee() {
        return additionalFee;
    }

    private static class UnitBasedTariffTypeStub implements TariffType {
        @Override
        public boolean isUnitBased() {
            return true;
        }

    }

    @Override
    public TariffType getType() {
        return new UnitBasedTariffTypeStub();
    }
}
