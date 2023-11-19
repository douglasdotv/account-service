package br.com.dv.account.dto.admin;

import br.com.dv.account.enums.UserRoleOperation;
import br.com.dv.account.validation.validator.EnumValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RoleUpdateRequest(
        @JsonProperty("user")
        @NotBlank
        String userEmail,
        @NotBlank
        String role,
        @EnumValue(enumClass = UserRoleOperation.class)
        UserRoleOperation operation
) {
}
