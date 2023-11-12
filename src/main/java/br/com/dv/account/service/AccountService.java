package br.com.dv.account.service;

import br.com.dv.account.dto.PaymentUploadRequest;
import br.com.dv.account.dto.PaymentUploadResponse;

import java.util.List;

public interface AccountService {

    PaymentUploadResponse addPayments(List<PaymentUploadRequest> payments);

    PaymentUploadResponse updatePayment(PaymentUploadRequest payment);

}
