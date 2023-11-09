package br.com.dv.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SignupResponse(
        String name,
        @JsonProperty("lastname")
        String lastName,
        String email
) {
}
