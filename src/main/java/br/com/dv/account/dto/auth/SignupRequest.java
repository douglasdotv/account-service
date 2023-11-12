package br.com.dv.account.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
        @NotBlank
        String name,
        @JsonProperty("lastname")
        @NotBlank
        String lastName,
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
