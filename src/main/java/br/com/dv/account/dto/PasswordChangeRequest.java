package br.com.dv.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record PasswordChangeRequest(@JsonProperty("new_password") @NotBlank String newPassword) {
}
