package br.com.dv.account.exception.custom;

public class EmployeeNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Employee with e-mail %s not found.";

    public EmployeeNotFoundException(String email) {
        super(String.format(EXCEPTION_MESSAGE, email));
    }

}
