package br.com.dv.account.exception.custom;

public class SamePasswordException extends RuntimeException {

    private final static String EXCEPTION_MESSAGE = "The new password must be different from the old one.";

    public SamePasswordException() {
        super(EXCEPTION_MESSAGE);
    }

}
