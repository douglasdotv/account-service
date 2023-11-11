package br.com.dv.account.exception.custom;

public class UserAuthenticationMismatchException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Authenticated user %s not found in database.";

    public UserAuthenticationMismatchException(String email) {
        super(String.format(EXCEPTION_MESSAGE, email));
    }

}
