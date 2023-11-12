package br.com.dv.account.service.employee;

import br.com.dv.account.dto.employee.PaymentResponse;
import br.com.dv.account.entity.Payment;
import br.com.dv.account.exception.custom.PaymentNotFoundException;
import br.com.dv.account.mapper.PaymentMapper;
import br.com.dv.account.repository.PaymentRepository;
import br.com.dv.account.validation.EmployeeValidationService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final EmployeeValidationService employeeValidationService;

    public EmployeeServiceImpl(PaymentRepository paymentRepository,
                               PaymentMapper paymentMapper,
                               EmployeeValidationService employeeValidationService) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.employeeValidationService = employeeValidationService;
    }

    @Override
    public List<PaymentResponse> getPaymentOrPayments(String employeeEmail, String period) {
        if (period != null) {
            employeeValidationService.validatePeriod(period);
        }

        List<Payment> payments = (period == null)
                ? findAllPayments(employeeEmail)
                : Collections.singletonList(findSinglePayment(employeeEmail, period));

        return mapPaymentsToResponse(payments);
    }

    private List<Payment> findAllPayments(String employeeEmail) {
        return paymentRepository.findAllByEmployeeEmailOrderByPeriodDescIgnoreCase(employeeEmail);
    }

    private Payment findSinglePayment(String employeeEmail, String period) {
        return paymentRepository.findByEmployeeEmailIgnoreCaseAndPeriod(employeeEmail, period)
                .orElseThrow(() -> new PaymentNotFoundException(employeeEmail, period));
    }

    private List<PaymentResponse> mapPaymentsToResponse(List<Payment> payments) {
        return paymentMapper.mapToPaymentResponseList(payments);
    }

}
