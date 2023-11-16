package br.com.dv.account.dto.accounting;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentUploadRequest(
        @JsonProperty("employee")
        @NotBlank
        String employeeEmail,
        @NotBlank
        String period,
        @NotNull
        Long salary
) {
}
