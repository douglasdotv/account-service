package br.com.dv.account.exception.custom;

public class UserEmailAlreadyExistsException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The e-mail '%s' is already in use.";

    public UserEmailAlreadyExistsException(String email) {
        super(String.format(EXCEPTION_MESSAGE, email));
    }

}
