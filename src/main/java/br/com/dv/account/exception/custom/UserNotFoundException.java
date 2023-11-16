package br.com.dv.account.exception.custom;

public class UserNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "User with e-mail %s not found.";

    public UserNotFoundException(String email) {
        super(String.format(EXCEPTION_MESSAGE, email));
    }

}
