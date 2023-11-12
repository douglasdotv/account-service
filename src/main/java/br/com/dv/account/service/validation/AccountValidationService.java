package br.com.dv.account.service.validation;

import br.com.dv.account.exception.custom.EmployeeNotFoundException;
import br.com.dv.account.exception.custom.InvalidPeriodException;
import br.com.dv.account.exception.custom.NonUniqueEmployeePeriodPairException;
import br.com.dv.account.repository.PaymentRepository;
import br.com.dv.account.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void validatePayment(String employeeEmail, String period) {
        validatePeriod(period);
        validateEmployeeExists(employeeEmail);
    }

    public void validatePayments(List<String> employeeEmails, List<String> periods) {
        for (int i = 0; i < employeeEmails.size(); ++i) {
            validatePayment(employeeEmails.get(i), periods.get(i));
        }
        ensureUniqueEmployeePeriodPair(employeeEmails, periods);
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

    private void ensureUniqueEmployeePeriodPair(List<String> employeeEmails, List<String> periods) {
        ensureUniqueEmployeePeriodPairWithinBatch(employeeEmails, periods);
        for (int i = 0; i < employeeEmails.size(); ++i) {
            ensureUniqueEmployeePeriodPairInDb(employeeEmails.get(i), periods.get(i));
        }
    }

    private void ensureUniqueEmployeePeriodPairWithinBatch(List<String> employeeEmails, List<String> periods) {
        Set<String> uniquePairs = new HashSet<>();
        for (int i = 0; i < employeeEmails.size(); ++i) {
            String pair = employeeEmails.get(i) + "-" + periods.get(i);
            if (!uniquePairs.add(pair)) {
                throw new NonUniqueEmployeePeriodPairException(employeeEmails.get(i), periods.get(i));
            }
        }
    }

    private void ensureUniqueEmployeePeriodPairInDb(String email, String period) {
        if (paymentRepository.existsByEmployeeEmailAndPeriod(email, period)) {
            throw new NonUniqueEmployeePeriodPairException(email, period);
        }
    }

}
