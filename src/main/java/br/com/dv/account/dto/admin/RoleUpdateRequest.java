package br.com.dv.account.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RoleUpdateRequest(
        @JsonProperty("user")
        @NotBlank
        String userEmail,
        @JsonProperty("role")
        @NotBlank
        String roleName,
        @NotBlank
        String operation
) {
}
