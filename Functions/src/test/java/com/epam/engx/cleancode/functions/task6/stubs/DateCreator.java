package com.epam.engx.cleancode.functions.task6.stubs;

import java.util.Calendar;
import java.util.Date;

public class DateCreator {

    public static Date createDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        return calendar.getTime();
    }
}
