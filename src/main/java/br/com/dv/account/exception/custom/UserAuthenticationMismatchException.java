package br.com.dv.account.exception.custom;

public class UserAuthenticationMismatchException extends RuntimeException {

    private final static String EXCEPTION_MESSAGE = "Authenticated user %s not found in database.";

    public UserAuthenticationMismatchException(String email) {
        super(String.format(EXCEPTION_MESSAGE, email));
    }

}
