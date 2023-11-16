package br.com.dv.account.dto.admin;

import br.com.dv.account.enums.StatusMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDeletionResponse(@JsonProperty("user") String userEmail, StatusMessage status) {
}
