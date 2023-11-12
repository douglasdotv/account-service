package br.com.dv.account.util;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public final class PeriodAndCurrencyFormatter {

    private static final String CANNOT_BE_INSTANTIATED_MESSAGE = "This class cannot be instantiated.";
    private static final String PERIOD_PATTERN = "MM-yyyy";
    private static final String DOLLARS_AND_CENTS_TEMPLATE = "%d dollar(s) %d cent(s)";

    private PeriodAndCurrencyFormatter() {
        throw new UnsupportedOperationException(CANNOT_BE_INSTANTIATED_MESSAGE);
    }

    public static String convertPeriodToMonthNameAndYearFormat(String period) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PERIOD_PATTERN);
        YearMonth yearMonth = YearMonth.parse(period, formatter);
        String monthName = yearMonth.getMonth().name();
        int year = yearMonth.getYear();
        return monthName.charAt(0) + monthName.substring(1).toLowerCase() + "-" + year;
    }

    public static String convertSalaryToDollarsAndCents(Long salaryInCents) {
        long dollars = salaryInCents / 100;
        long cents = salaryInCents % 100;
        return String.format(DOLLARS_AND_CENTS_TEMPLATE, dollars, cents);
    }

}
