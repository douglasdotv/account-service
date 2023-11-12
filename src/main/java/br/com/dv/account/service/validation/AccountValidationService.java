package br.com.dv.account.service.validation;

import br.com.dv.account.dto.PaymentUploadRequest;
import br.com.dv.account.exception.custom.EmployeeNotFoundException;
import br.com.dv.account.exception.custom.InvalidPeriodException;
import br.com.dv.account.exception.custom.NonUniqueEmployeePeriodPairException;
import br.com.dv.account.repository.PaymentRepository;
import br.com.dv.account.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class AccountValidationService {

    private static final String EXPECTED_PERIOD_FORMAT = "MM-yyyy";

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    public AccountValidationService(UserRepository userRepository,
                                    PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
    }

    public void validatePayment(PaymentUploadRequest payment) {
        validatePeriod(payment.period());
        validateEmployeeExists(payment.employeeEmail());
    }

    public void validatePayments(List<PaymentUploadRequest> payments) {
        payments.forEach(this::validatePayment);
        ensureUniqueEmployeePeriodPair(payments);
    }

    public void validatePeriod(String period) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(EXPECTED_PERIOD_FORMAT);
            YearMonth.parse(period, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidPeriodException();
        }
    }

    private void validateEmployeeExists(String email) {
        if (!userRepository.existsByEmailIgnoreCase(email)) {
            throw new EmployeeNotFoundException(email);
        }
    }

    private void ensureUniqueEmployeePeriodPair(List<PaymentUploadRequest> payments) {
        ensureUniqueEmployeePeriodPairWithinBatch(payments);
        payments.forEach(payment -> ensureUniqueEmployeePeriodPairInDb(payment.employeeEmail(), payment.period()));
    }

    private void ensureUniqueEmployeePeriodPairWithinBatch(List<PaymentUploadRequest> payments) {
        Set<String> uniquePairs = new HashSet<>();
        payments.forEach(payment -> {
            String pair = payment.employeeEmail() + payment.period();
            if (uniquePairs.contains(pair)) {
                throw new NonUniqueEmployeePeriodPairException(payment.employeeEmail(), payment.period());
            }
            uniquePairs.add(pair);
        });
    }

    private void ensureUniqueEmployeePeriodPairInDb(String email, String period) {
        if (paymentRepository.existsByEmployeeEmailAndPeriod(email, period)) {
            throw new NonUniqueEmployeePeriodPairException(email, period);
        }
    }

}
