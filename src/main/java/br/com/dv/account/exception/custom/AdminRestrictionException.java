package br.com.dv.account.exception.custom;

public class AdminRestrictionException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Cannot remove ADMINISTRATOR role.";

    public AdminRestrictionException() {
        super(EXCEPTION_MESSAGE);
    }

}
