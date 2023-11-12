package br.com.dv.account.validation;

import br.com.dv.account.dto.accounting.PaymentUploadRequest;
import br.com.dv.account.exception.custom.EmployeeNotFoundException;
import br.com.dv.account.exception.custom.NonUniqueEmployeePeriodPairException;
import br.com.dv.account.repository.PaymentRepository;
import br.com.dv.account.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccountingValidationService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final CommonValidationService commonValidationService;

    public AccountingValidationService(CommonValidationService commonValidationService,
                                       UserRepository userRepository,
                                       PaymentRepository paymentRepository) {
        this.commonValidationService = commonValidationService;
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

    private void validatePeriod(String period) {
        commonValidationService.validatePeriod(period);
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
