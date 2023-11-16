package br.com.dv.account.exception.custom;

public class LastRoleException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Cannot remove ROLE_%s: user must have at least one role.";

    public LastRoleException(String role) {
        super(String.format(EXCEPTION_MESSAGE, role));
    }

}
