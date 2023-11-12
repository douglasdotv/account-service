package br.com.dv.account.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record PasswordChangeRequest(@JsonProperty("new_password") @NotBlank String newPassword) {
}
