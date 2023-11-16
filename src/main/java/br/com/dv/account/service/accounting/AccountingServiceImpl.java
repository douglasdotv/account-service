package br.com.dv.account.service.accounting;

import br.com.dv.account.dto.accounting.PaymentUploadRequest;
import br.com.dv.account.dto.accounting.PaymentUploadResponse;
import br.com.dv.account.entity.Payment;
import br.com.dv.account.entity.User;
import br.com.dv.account.enums.StatusMessage;
import br.com.dv.account.exception.custom.EmployeeNotFoundException;
import br.com.dv.account.exception.custom.PaymentNotFoundException;
import br.com.dv.account.mapper.PaymentMapper;
import br.com.dv.account.repository.PaymentRepository;
import br.com.dv.account.repository.UserRepository;
import br.com.dv.account.validation.AccountingValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountingServiceImpl implements AccountingService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final AccountingValidationService accountingValidationService;

    public AccountingServiceImpl(UserRepository userRepository,
                                 PaymentRepository paymentRepository,
                                 PaymentMapper paymentMapper,
                                 AccountingValidationService accountingValidationService) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.accountingValidationService = accountingValidationService;
    }

    @Override
    @Transactional
    public PaymentUploadResponse addPayments(List<PaymentUploadRequest> paymentsRequest) {
        accountingValidationService.validatePayments(paymentsRequest);

        List<Payment> payments = paymentsRequest.stream()
                .map(this::mapAndSetEmployeeToPayment)
                .collect(Collectors.toList());

        paymentRepository.saveAll(payments);

        return new PaymentUploadResponse(StatusMessage.ADDED_SUCCESSFULLY);
    }

    @Override
    @Transactional
    public PaymentUploadResponse updatePayment(PaymentUploadRequest paymentRequest) {
        accountingValidationService.validatePayment(paymentRequest);

        Payment payment = findPaymentByEmployeeEmailAndPeriod(paymentRequest.employeeEmail(), paymentRequest.period());
        payment.setSalary(paymentRequest.salary());

        paymentRepository.save(payment);

        return new PaymentUploadResponse(StatusMessage.UPDATED_SUCCESSFULLY);
    }

    private Payment mapAndSetEmployeeToPayment(PaymentUploadRequest paymentRequest) {
        Payment payment = paymentMapper.mapToPayment(paymentRequest);
        User employee = findUserByEmail(paymentRequest.employeeEmail());
        employee.addPayment(payment);
        return payment;
    }

    private Payment findPaymentByEmployeeEmailAndPeriod(String email, String period) {
        return paymentRepository.findByEmployeeEmailIgnoreCaseAndPeriod(email, period)
                .orElseThrow(() -> new PaymentNotFoundException(email, period));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new EmployeeNotFoundException(email));
    }

}
