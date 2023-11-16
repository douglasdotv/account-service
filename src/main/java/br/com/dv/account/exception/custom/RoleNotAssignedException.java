package br.com.dv.account.exception.custom;

public class RoleNotAssignedException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The user does not have the role %s.";

    public RoleNotAssignedException(String role) {
        super(String.format(EXCEPTION_MESSAGE, role));
    }

}
