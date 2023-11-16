package br.com.dv.account.exception.custom;

public class RoleNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Role %s not found.";

    public RoleNotFoundException(String role) {
        super(String.format(EXCEPTION_MESSAGE, role));
    }

}
