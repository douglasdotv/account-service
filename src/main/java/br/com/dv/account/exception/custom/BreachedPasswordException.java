package br.com.dv.account.exception.custom;

public class BreachedPasswordException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The chosen password is breached. Please choose another one.";

    public BreachedPasswordException() {
        super(EXCEPTION_MESSAGE);
    }

}
