package br.com.dv.account.exception.custom;

public class UserEmailAlreadyExistsException extends RuntimeException {

    private final static String EXCEPTION_MESSAGE = "The e-mail '%s' is already in use.";

    public UserEmailAlreadyExistsException(String email) {
        super(String.format(EXCEPTION_MESSAGE, email));
    }

}
