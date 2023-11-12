package br.com.dv.account.mapper;

import br.com.dv.account.dto.employee.PaymentResponse;
import br.com.dv.account.dto.accounting.PaymentUploadRequest;
import br.com.dv.account.entity.Payment;
import br.com.dv.account.util.PeriodAndCurrencyFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", imports = PeriodAndCurrencyFormatter.class)
public abstract class PaymentMapper {

    private static final String SALARY_CONVERSION_EXPRESSION =
            "java(PeriodAndCurrencyFormatter.convertSalaryToDollarsAndCents(payment.getSalary()))";
    private static final String PERIOD_CONVERSION_EXPRESSION =
            "java(PeriodAndCurrencyFormatter.convertPeriodToMonthNameAndYearFormat(payment.getPeriod()))";

    public abstract List<Payment> mapToPaymentList(List<PaymentUploadRequest> paymentsRequest);

    @SuppressWarnings("unused")
    @Mapping(target = "name", source = "employee.name")
    @Mapping(target = "lastName", source = "employee.lastName")
    @Mapping(target = "salary", expression = SALARY_CONVERSION_EXPRESSION)
    @Mapping(target = "period", expression = PERIOD_CONVERSION_EXPRESSION)
    public abstract PaymentResponse mapToPaymentResponse(Payment payment);

    public abstract List<PaymentResponse> mapToPaymentResponseList(List<Payment> payments);

}
