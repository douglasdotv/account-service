package br.com.dv.account.exception.custom;

public class PaymentNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Payment not found for e-mail %s and period %s.";

    public PaymentNotFoundException(String email, String period) {
        super(String.format(EXCEPTION_MESSAGE, email, period));
    }

}
