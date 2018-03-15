package com.epam.engx.cleancode.functions.task6.stubs;

import com.epam.engx.cleancode.functions.task6.thirdpartyjar.Tariff;
import com.epam.engx.cleancode.functions.task6.thirdpartyjar.TariffType;

public class NoRateTariffStub implements Tariff {

    private double additionalFee;

    public NoRateTariffStub() {
        additionalFee = 0;
    }

    public NoRateTariffStub(double additionalFee) {
        this.additionalFee = additionalFee;
    }

    @Override
    public double getAdditionalFee() {
        return additionalFee;
    }

    private static class NoRateTariffTypeStub implements TariffType {
        @Override
        public boolean isUnitBased() {
            return false;
        }
    }

    @Override
    public TariffType getType() {
        return new NoRateTariffTypeStub();
    }
}