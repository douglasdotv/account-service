package br.com.dv.account.exception.custom;

public class CannotLockAdminException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Cannot lock ADMINISTRATOR.";

    public CannotLockAdminException() {
        super(EXCEPTION_MESSAGE);
    }

}
