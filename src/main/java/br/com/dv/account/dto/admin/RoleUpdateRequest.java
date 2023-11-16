package br.com.dv.account.dto.admin;

import br.com.dv.account.enums.AdminOperation;
import br.com.dv.account.validation.validator.EnumValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoleUpdateRequest(
        @JsonProperty("user")
        @NotBlank
        String userEmail,
        @NotBlank
        String role,
        @EnumValue(enumClass = AdminOperation.class)
        AdminOperation operation
) {
}
