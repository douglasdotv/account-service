package br.com.dv.account.service.employee;

import br.com.dv.account.dto.employee.PaymentResponse;

import java.util.List;

public interface EmployeeService {

    List<PaymentResponse> getPaymentOrPayments(String employeeEmail, String period);

}
