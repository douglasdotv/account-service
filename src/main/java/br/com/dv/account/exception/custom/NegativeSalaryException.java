package br.com.dv.account.exception.custom;

public class NegativeSalaryException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Salary cannot be negative. Salary: %d.";

    public NegativeSalaryException(Long salary) {
        super(String.format(EXCEPTION_MESSAGE, salary));
    }

}
