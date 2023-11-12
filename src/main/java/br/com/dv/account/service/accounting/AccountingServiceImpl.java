package br.com.dv.account.service.accounting;

import br.com.dv.account.dto.accounting.PaymentUploadRequest;
import br.com.dv.account.dto.accounting.PaymentUploadResponse;
import br.com.dv.account.entity.Payment;
import br.com.dv.account.entity.User;
import br.com.dv.account.exception.custom.EmployeeNotFoundException;
import br.com.dv.account.exception.custom.PaymentNotFoundException;
import br.com.dv.account.mapper.PaymentMapper;
import br.com.dv.account.repository.PaymentRepository;
import br.com.dv.account.repository.UserRepository;
import br.com.dv.account.validation.AccountingValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountingServiceImpl implements AccountingService {

    private static final String ADDED_SUCCESSFULLY_STATUS = "Added successfully!";
    private static final String UPDATED_SUCCESSFULLY_STATUS = "Updated successfully!";

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

        List<Payment> payments = paymentMapper.mapToPaymentList(paymentsRequest);
        payments.forEach(payment -> {
            User employee = userRepository.findByEmailIgnoreCase(payment.getEmployeeEmail())
                    .orElseThrow(() -> new EmployeeNotFoundException(payment.getEmployeeEmail()));

            employee.addPayment(payment);
        });
        paymentRepository.saveAll(payments);

        return new PaymentUploadResponse(ADDED_SUCCESSFULLY_STATUS);
    }

    @Override
    @Transactional
    public PaymentUploadResponse updatePayment(PaymentUploadRequest paymentRequest) {
        accountingValidationService.validatePayment(paymentRequest);

        Payment payment = paymentRepository.findByEmployeeEmailIgnoreCaseAndPeriod(paymentRequest.employeeEmail(),
                        paymentRequest.period())
                .orElseThrow(() -> new PaymentNotFoundException(paymentRequest.employeeEmail(),
                        paymentRequest.period()));
        payment.setSalary(paymentRequest.salary());
        paymentRepository.save(payment);

        return new PaymentUploadResponse(UPDATED_SUCCESSFULLY_STATUS);
    }

}
