package br.com.dv.account.service;

import br.com.dv.account.dto.SignupRequest;
import br.com.dv.account.dto.SignupResponse;
import br.com.dv.account.exception.custom.InvalidEmailException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final static String EXPECTED_EMAIL_DOMAIN = "@acme.com";

    @Override
    public SignupResponse signUp(SignupRequest signupRequest) {
        if (!validateEmail(signupRequest.email())) {
            throw new InvalidEmailException(signupRequest.email());
        }

        return new SignupResponse(
                signupRequest.name(),
                signupRequest.lastName(),
                signupRequest.email()
        );
    }

    private boolean validateEmail(String email) {
        return email.toLowerCase().endsWith(EXPECTED_EMAIL_DOMAIN);
    }

}
