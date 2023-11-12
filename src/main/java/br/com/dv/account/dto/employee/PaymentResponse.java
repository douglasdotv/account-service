package br.com.dv.account.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentResponse(
        String name,
        @JsonProperty("lastname")
        String lastName,
        String period,
        String salary
) {
}
