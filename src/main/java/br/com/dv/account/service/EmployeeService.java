package br.com.dv.account.service;

import br.com.dv.account.dto.PaymentResponse;

public interface EmployeeService {

    PaymentResponse getPayment(String userEmail);

}
