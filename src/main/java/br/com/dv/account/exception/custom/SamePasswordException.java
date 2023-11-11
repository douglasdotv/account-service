package br.com.dv.account.exception.custom;

public class SamePasswordException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The new password must be different from the old one.";

    public SamePasswordException() {
        super(EXCEPTION_MESSAGE);
    }

}
