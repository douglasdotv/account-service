package br.com.dv.account.exception.custom;

public class InvalidEmailException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "The e-mail %s does not end with the expected domain @acme.com.";

    public InvalidEmailException(String email) {
        super(String.format(EXCEPTION_MESSAGE, email));
    }

}
