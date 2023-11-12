package br.com.dv.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentResponse(
        String name,
        @JsonProperty("lastname")
        String lastName,
        String period,
        String salary
) {
}
