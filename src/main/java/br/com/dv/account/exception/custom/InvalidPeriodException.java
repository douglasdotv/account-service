package br.com.dv.account.exception.custom;

public class InvalidPeriodException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Period format must be mm-YYYY with valid month and year.";

    public InvalidPeriodException() {
        super(EXCEPTION_MESSAGE);
    }

}
