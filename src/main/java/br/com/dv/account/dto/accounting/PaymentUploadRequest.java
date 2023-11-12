package br.com.dv.account.dto.accounting;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentUploadRequest(
        @JsonProperty("employee")
        @NotBlank
        String employeeEmail,
        @NotBlank
        String period,
        @Positive
        @NotNull
        Long salary
) {
}
