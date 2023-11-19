package br.com.dv.account.dto.admin;

import br.com.dv.account.enums.UserAccessOperation;
import br.com.dv.account.validation.validator.EnumValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record AccessUpdateRequest(
        @JsonProperty("user")
        @NotBlank
        String userEmail,
        @EnumValue(enumClass = UserAccessOperation.class)
        UserAccessOperation operation
) {
}
