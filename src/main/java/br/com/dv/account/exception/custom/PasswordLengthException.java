package br.com.dv.account.exception.custom;

public class PasswordLengthException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The password must be at least 12 characters long.";

    public PasswordLengthException() {
        super(EXCEPTION_MESSAGE);
    }

}
