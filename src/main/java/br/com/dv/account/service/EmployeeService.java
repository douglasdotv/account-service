package br.com.dv.account.service;

import br.com.dv.account.dto.PaymentResponse;

import java.util.List;

public interface EmployeeService {

    List<PaymentResponse> getPaymentOrPayments(String employeeEmail, String period);

}
