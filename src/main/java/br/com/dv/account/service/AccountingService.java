package br.com.dv.account.service;

import br.com.dv.account.dto.PaymentUploadRequest;
import br.com.dv.account.dto.PaymentUploadResponse;

import java.util.List;

public interface AccountingService {

    PaymentUploadResponse addPayments(List<PaymentUploadRequest> paymentsRequest);

    PaymentUploadResponse updatePayment(PaymentUploadRequest paymentRequest);

}
