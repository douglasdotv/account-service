package br.com.dv.account.exception.custom;

public class RoleConflictException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Administrative and business roles cannot be combined.";

    public RoleConflictException() {
        super(EXCEPTION_MESSAGE);
    }

}
