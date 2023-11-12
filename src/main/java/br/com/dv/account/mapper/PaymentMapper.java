package br.com.dv.account.mapper;

import br.com.dv.account.dto.PaymentUploadRequest;
import br.com.dv.account.entity.Payment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PaymentMapper {

    public abstract List<Payment> paymentUploadRequestListToPaymentList(List<PaymentUploadRequest>
                                                                                paymentUploadRequests);

}
