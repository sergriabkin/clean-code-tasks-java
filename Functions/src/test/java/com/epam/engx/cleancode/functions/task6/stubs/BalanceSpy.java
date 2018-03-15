package com.epam.engx.cleancode.functions.task6.stubs;

import com.epam.engx.cleancode.functions.task6.thirdpartyjar.Balance;
import org.junit.Assert;

public class BalanceSpy implements Balance {

    Double updatedSum = 0.0;

    @Override
    public void updateBalance(Double sum) {
        updatedSum += sum;
    }

    public void verifyUpdatedSum(Double expectedSum){
        Assert.assertEquals(expectedSum, updatedSum, HistoryStub.DELTA);
    }
}
