package br.com.dv.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentResponse(
        @JsonProperty("id")
        Long userId,
        String name,
        @JsonProperty("lastname")
        String lastName,
        String email
) {
}
