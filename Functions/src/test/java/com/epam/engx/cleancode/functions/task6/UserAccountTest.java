package com.epam.engx.cleancode.functions.task6;

import com.epam.engx.cleancode.functions.task6.stubs.*;
import com.epam.engx.cleancode.functions.task6.thirdpartyjar.Service;
import com.epam.engx.cleancode.functions.task6.thirdpartyjar.Tariff;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static com.epam.engx.cleancode.functions.task6.stubs.DateCreator.createDate;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class UserAccountTest {

    public static final double UNIT_RATE = 0.8;

    private CalculationHistoryServiceStub calculationHistoryServiceStub;
    private Service serviceStub = new ServiceStub();
    private Service multiTariffServiceStub = new MultiTariffServiceStub(asList(new UnitBasedTariffStub(), new NoRateTariffStub()));
    private BalanceSpy balanceSpy = new BalanceSpy();

    private UserAccount userAccount = new UserAccount();


    @Before
    public void setUp() {
        setupPaymentDates();
        userAccount.setBalance(balanceSpy);
        userAccount.setServices(singletonList(serviceStub));
    }

    private void setupPaymentDates() {
        List<Date> paymentDates = new ArrayList<>();
        paymentDates.add(createDate(2001, 2, 22));
        paymentDates.add(createDate(2001, 1, 23));
        paymentDates.add(createDate(2001, 4, 19));
        userAccount.setPaymentDates(paymentDates);
    }

    @Test
    public void shouldNotApplyPaymentWhenAllFeesAlreadyRecalculated() {

        setupUncalculatedFees(new HashMap<Date, Double>());

        userAccount.recalculateBalance();

        verifyAppliedSum(0.0);
    }

    @Test
    public void shouldApplySumOfAllNotCalculatedFees() {

        setupUncalculatedFees(new HashMap<Date, Double>() {{
            put(createDate(2001, 4, 20), 200.0);
            put(createDate(2001, 5, 22), 150.0);
        }});

        userAccount.recalculateBalance();

        verifyAppliedSum(350.0 * UNIT_RATE);

    }

    @Test
    public void shouldShouldApplySumForTariffWithHighestRate() {

        userAccount.setServices(singletonList(multiTariffServiceStub));
        setupUncalculatedFees(new HashMap<Date, Double>() {{
            put(createDate(2001, 4, 20), 200.0);
            put(createDate(2001, 5, 22), 150.0);
        }});

        userAccount.recalculateBalance();

        verifyAppliedSum(350.0);

    }

    @Test
    public void shouldApplySumForTariffWithAdditionalFeeForEachUncalculatedFee() {

        setupTariffs(Arrays.<Tariff>asList(new NoRateTariffStub(10.0), new NoRateTariffStub()));
        setupUncalculatedFees(new HashMap<Date, Double>() {{
            put(createDate(2001, 4, 20), 200.0);
            put(createDate(2001, 5, 22), 150.0);
        }});

        userAccount.recalculateBalance();

        verifyAppliedSum(350.0 + 10.0 + 10.0);
    }

    @Test
    public void shouldApplySumForTariffWithAdditionalFeeWhenItsHigherThenOtherTariff() {
        setupTariffs(asList(new NoRateTariffStub(), new UnitBasedTariffStub(50.0)));
        setupUncalculatedFees(new HashMap<Date, Double>() {{
            put(createDate(2001, 4, 20), 200.0);
        }});

        userAccount.recalculateBalance();

        verifyAppliedSum(200.0 * UNIT_RATE + 50.0);
    }

    @Test
    public void shouldApplySumForTariffWithHighestRateWhenItsHigherThenOtherTariff() {
        setupTariffs(asList(new NoRateTariffStub(), new UnitBasedTariffStub(10.0)));
        setupUncalculatedFees(new HashMap<Date, Double>() {{
            put(createDate(2001, 4, 20), 200.0);
        }});

        userAccount.recalculateBalance();

        verifyAppliedSum(200.0);
    }

    @Test
    public void shouldApplySumOfAllNotCalculatedFeesForAllServices() {

        //given
        userAccount.setServices(Arrays.<Service>asList(new ServiceStub(), new SecondServiceStub()));
        CalculationHistoryForMultiServiceStub calculationHistoryService =
                new CalculationHistoryForMultiServiceStub(new HashMap<Date, Double>() {{
                    put(createDate(2001, 4, 20), 200.0);
                }}, new HashMap<Date, Double>() {{
                    put(createDate(2001, 6, 25), 120.0);
                    put(createDate(2001, 5, 25), 180.0);
                }});
        userAccount.setCalculationHistoryService(calculationHistoryService);

        //when
        userAccount.recalculateBalance();

        //then
        calculationHistoryService.verifyAppliedSumForService(200.0 * UNIT_RATE, ServiceStub.class);
        calculationHistoryService.verifyAppliedSumForService((120.0 + 180.0) * UNIT_RATE, SecondServiceStub.class);
        balanceSpy.verifyUpdatedSum((200.0 + 120.0 + 180.0) * UNIT_RATE);
    }

    private void setupTariffs(List<Tariff> tariffs) {
        userAccount.setServices(Collections.<Service>singletonList(
                new MultiTariffServiceStub(tariffs)));
    }

    private void setupUncalculatedFees(HashMap<Date, Double> uncalculatedFees) {
        calculationHistoryServiceStub = new CalculationHistoryServiceStub(uncalculatedFees);
        userAccount.setCalculationHistoryService(calculationHistoryServiceStub);
    }

    private void verifyAppliedSum(double expectedSum) {
        balanceSpy.verifyUpdatedSum(expectedSum);
        calculationHistoryServiceStub.verifyAppliedSum(expectedSum);
    }

}
