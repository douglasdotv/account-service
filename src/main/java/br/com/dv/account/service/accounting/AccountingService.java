package br.com.dv.account.service.accounting;

import br.com.dv.account.dto.accounting.PaymentUploadRequest;
import br.com.dv.account.dto.accounting.PaymentUploadResponse;

import java.util.List;

public interface AccountingService {

    PaymentUploadResponse addPayments(List<PaymentUploadRequest> paymentsRequest);

    PaymentUploadResponse updatePayment(PaymentUploadRequest paymentRequest);

}
