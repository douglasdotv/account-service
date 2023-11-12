package br.com.dv.account.service.validation;

import br.com.dv.account.dto.PasswordChangeRequest;
import br.com.dv.account.dto.SignupRequest;
import br.com.dv.account.exception.custom.*;
import br.com.dv.account.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserValidationService {

    private static final String EXPECTED_EMAIL_DOMAIN = "@acme.com";
    private static final int MINIMUM_PASSWORD_LENGTH = 12;
    private static final Set<String> BREACHED_PASSWORDS = Set.of(
            "PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember"
    );

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserValidationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void validateSignup(SignupRequest signup) {
        validateEmailDomain(signup.email());
        validateExistingEmail(signup.email());
        validatePasswordLength(signup.password());
        validateBreachedPassword(signup.password());
    }

    public void validatePasswordChange(PasswordChangeRequest passwordChange, String currentPassword) {
        validatePasswordLength(passwordChange.newPassword());
        validateBreachedPassword(passwordChange.newPassword());
        validateSamePassword(passwordChange.newPassword(), currentPassword);
    }

    private void validateEmailDomain(String email) {
        if (!email.toLowerCase().endsWith(EXPECTED_EMAIL_DOMAIN)) {
            throw new InvalidEmailDomainException(email);
        }
    }

    private void validateExistingEmail(String email) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new UserEmailAlreadyExistsException(email);
        }
    }

    private void validatePasswordLength(String password) {
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new PasswordLengthException();
        }
    }

    private void validateBreachedPassword(String password) {
        if (BREACHED_PASSWORDS.contains(password)) {
            throw new BreachedPasswordException();
        }
    }

    private void validateSamePassword(String newPassword, String currentPassword) {
        if (passwordEncoder.matches(newPassword, currentPassword)) {
            throw new SamePasswordException();
        }
    }

}
