package com.epam.engx.cleancode.functions.task6;

import com.epam.engx.cleancode.functions.task6.thirdpartyjar.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserAccount {

    private static final long EPOCH_TIMESTAMP = 0;

    Balance balance;

    List<Date> paymentDates;

    private List<Service> services;

    private CalculationHistoryService calculationHistoryService;

    public void recalculateBalance() {
        double unitRate = 0.8;

        for (Service service : services) {
            List<Tariff> tariffs = service.getTariffs();
            History h = calculationHistoryService.retrieveHistory(service);

            //find last calculation date
            long latest = EPOCH_TIMESTAMP;
            for (Date p : paymentDates)
                latest = Math.max(p.getTime(), latest);
            Date d = new Date(latest);

            Double highestTariff = 0.0;
            if (!tariffs.isEmpty()) {
                for (Tariff tariff : tariffs) {
                    TariffType tariffType = tariff.getType();
                    highestTariff = Math.max(highestTariff, calculateUnapplied(tariff, d, h, unitRate, tariffType, service));
                }
            }

            h.applyRecalculation(highestTariff, unitRate);
            balance.updateBalance(highestTariff);

        }
    }

    private Double calculateUnapplied(Tariff tariff, Date lastCalculationDate, History h, double unitRate, TariffType t, Service service) {
        Map<Date, Double> fees = h.getAllFees(tariff, service);
        Double sum = 0.0;
        for (Date date : fees.keySet()) {
            if (date.after(lastCalculationDate))
                sum += fees.get(date) * ((t.isUnitBased()) ? unitRate : 1) + tariff.getAdditionalFee();
        }
        return sum;
    }

    public void setCalculationHistoryService(CalculationHistoryService calculationHistoryService) {
        this.calculationHistoryService = calculationHistoryService;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public void setPaymentDates(List<Date> paymentDates) {
        this.paymentDates = paymentDates;
    }
}
