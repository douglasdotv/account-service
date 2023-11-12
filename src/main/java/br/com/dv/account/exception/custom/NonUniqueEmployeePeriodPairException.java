package br.com.dv.account.exception.custom;

public class NonUniqueEmployeePeriodPairException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Duplicate employee %s and period %s pair found.";

    public NonUniqueEmployeePeriodPairException(String email, String period) {
        super(String.format(EXCEPTION_MESSAGE, email, period));
    }

}
