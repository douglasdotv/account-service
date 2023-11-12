package br.com.dv.account.service;

import br.com.dv.account.dto.PaymentUploadRequest;
import br.com.dv.account.dto.PaymentUploadResponse;
import br.com.dv.account.entity.Payment;
import br.com.dv.account.entity.User;
import br.com.dv.account.exception.custom.EmployeeNotFoundException;
import br.com.dv.account.exception.custom.PaymentNotFoundException;
import br.com.dv.account.mapper.PaymentMapper;
import br.com.dv.account.repository.PaymentRepository;
import br.com.dv.account.repository.UserRepository;
import br.com.dv.account.service.validation.AccountValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private static final String ADDED_SUCCESSFULLY_STATUS = "Added successfully!";
    private static final String UPDATED_SUCCESSFULLY_STATUS = "Updated successfully!";

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final AccountValidationService accountValidationService;
    private final PaymentMapper paymentMapper;

    public AccountServiceImpl(PaymentRepository paymentRepository,
                              UserRepository userRepository,
                              AccountValidationService accountValidationService,
                              PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.accountValidationService = accountValidationService;
        this.paymentMapper = paymentMapper;
    }

    @Override
    @Transactional
    public PaymentUploadResponse addPayments(List<PaymentUploadRequest> paymentsRequest) {
        accountValidationService.validatePayments(paymentsRequest);

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
        accountValidationService.validatePayment(paymentRequest);

        Payment payment = paymentRepository.findByEmployeeEmailIgnoreCaseAndPeriod(paymentRequest.employeeEmail(),
                        paymentRequest.period())
                .orElseThrow(() -> new PaymentNotFoundException(paymentRequest.employeeEmail(),
                        paymentRequest.period()));
        payment.setSalary(paymentRequest.salary());
        paymentRepository.save(payment);

        return new PaymentUploadResponse(UPDATED_SUCCESSFULLY_STATUS);
    }

}
