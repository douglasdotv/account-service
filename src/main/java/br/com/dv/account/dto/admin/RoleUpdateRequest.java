package br.com.dv.account.dto.admin;

import br.com.dv.account.enums.AdminOperation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoleUpdateRequest(
        @JsonProperty("user")
        @NotBlank
        String userEmail,
        @NotBlank
        String role,
        @NotNull
        AdminOperation operation
) {
}
